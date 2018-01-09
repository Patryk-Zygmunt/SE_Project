from daemon import Daemon
import sys
import time
from rest import InfoJsonBuilder, Client
from configuration import Config
import collector
import datetime
import logging


def assistant(fun1, fun2):
    try:
        result = fun2()
        fun1(result)
    except collector.CollectorException as ex:
        logging.exception(ex)


class DaemonLogger(Daemon):
    delay = 5
    last_update = None
    client = None
    config = None

    def setup(self):
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
        self.config = Config()
        self.config.start()
        self.delay = self.config.get_send_frequency()
        self.client = Client(self.config)
        logging.info("Agent initialized successfully!")

    def loop(self):
        data = self.__collect_data()
        response = self.client.send_info(data.to_json())
        logging.debug("response: {}".format(response.status))

    def __collect_data(self) -> InfoJsonBuilder:
        sys_info = collector.SystemDataCollector()
        logs = collector.JournalLogCollector()
        logs.set_from_config(self.config.logs_config)
        if self.last_update is not None:
            logs.set_since_date(self.last_update)

        json_b = InfoJsonBuilder()
        assistant(json_b.add_name, sys_info.get_hostname)
        assistant(json_b.add_disc_operations, sys_info.drive_operations)
        assistant(json_b.add_io_interface, sys_info.interface_load)
        assistant(json_b.add_ram, sys_info.ram_usage)
        assistant(json_b.add_processor, sys_info.processor_usage)
        assistant(json_b.add_discs_space, sys_info.drive_space)
        assistant(json_b.add_temperature, sys_info.get_temp)
        assistant(json_b.add_mac, sys_info.get_mac)
        if self.config.logs_config.send:
            assistant(json_b.add_logs, logs.collect)

        return json_b

    def run(self):
        self.setup()
        while True:
            try:
                self.loop()
                self.last_update = datetime.datetime.now()
            except Exception as ex:
                logging.exception(ex)
            finally:
                time.sleep(self.delay)


if __name__ == "__main__":
    daemon = DaemonLogger('/tmp/daemon-example.pid')
    if len(sys.argv) == 2:
        if 'start' == sys.argv[1]:
            daemon.start()
        elif 'stop' == sys.argv[1]:
            daemon.stop()
        elif 'restart' == sys.argv[1]:
            daemon.restart()
        elif 'no-daemon' == sys.argv[1]:
            daemon.run()
        else:
            print("Unknown command")
            sys.exit(2)
        sys.exit(0)
    else:
        print("usage: %s start|stop|restart|no-daemon" % sys.argv[0])
        sys.exit(2)
