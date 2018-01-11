from daemon import Daemon
import sys
import time
from rest import InfoJsonBuilder, Client
from configuration import Config
import collector
import datetime
import logging


class DaemonLogger(Daemon):
    last_update = None
    client = None
    config = None
    agentLog = None

    def setup(self):
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
        self.config = Config()
        self.config.start()
        self.client = Client(self.config)
        self.agentLog = collector.AgentLogCollector()
        logging.info("Agent initialized successfully!")

    def loop(self):
        data = self.__collect_data()
        response = self.client.send_info(data.to_json())
        self.agentLog.logs.clear()
        logging.debug("response: {}".format(response.status))

    def __collect_data(self) -> InfoJsonBuilder:
        sys_info = collector.SystemDataCollector()
        logs = collector.JournalLogCollector()
        logs.set_from_config(self.config.logs_config)
        if self.last_update is not None:
            logs.set_since_date(self.last_update)

        json_b = InfoJsonBuilder()
        self.exc_assist(json_b.add_name, sys_info.get_hostname)
        self.exc_assist(json_b.add_disc_operations, sys_info.drive_operations)
        self.exc_assist(json_b.add_io_interface, sys_info.interface_load)
        self.exc_assist(json_b.add_ram, sys_info.ram_usage)
        self.exc_assist(json_b.add_processor, sys_info.processor_usage)
        self.exc_assist(json_b.add_discs_space, sys_info.drive_space)
        self.exc_assist(json_b.add_temperature, sys_info.get_temp)
        self.exc_assist(json_b.add_mac, sys_info.get_mac)
        if self.config.logs_config.send:
            self.exc_assist(json_b.add_logs, self.agentLog.add_to_list, logs.collect)
        return json_b

    def exc_assist(self, *args):
        try:
            result = args[-1]()
            for i in range(2, len(args)+1):
                result = args[-i](result)
            return result

        except collector.CollectorException as ex:
            logging.exception(ex)
            self.agentLog.add_collector_log(ex)
        except Exception as ex:
            logging.exception(ex)
            self.agentLog.add_log(ex)

    def run(self):
        self.setup()
        while True:
            try:
                self.loop()
                self.last_update = datetime.datetime.now()
            except Exception as ex:
                logging.exception(ex)
                self.agentLog.add_log(ex)
            finally:
                time.sleep(self.config.get_send_frequency())


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
