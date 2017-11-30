from daemon import Daemon
import sys
import time
from rest import InfoJsonBuilder, Client
import collector
import datetime


class DaemonLogger(Daemon):
    delay = 5
    last_update = None
    client = None

    def setup(self):
        self.client = Client('localhost', 8081)

    def loop(self):
        data = self.__collect_data()
        response = self.client.send_info(data.to_json())
        #TODO do something with response?

    def __collect_data(self) -> InfoJsonBuilder:
        sys_info = collector.SystemDataCollector()
        logs = collector.JournalLogCollector()
        logs.set_reverse()
        logs.set_priority(collector.JournalLogCollector.Priority.ERROR)
        if self.last_update is not None:
            logs.set_since_date(self.last_update)

        json_b = InfoJsonBuilder()
        json_b.add_name(sys_info.get_hostname())
        json_b.add_errLogs(logs.collect())
        json_b.add_iOperSec(sys_info.drive_operations())
        json_b.add_interfaceIO(sys_info.interface_load())
        json_b.add_ramTaken(sys_info.ram_usage())
        json_b.add_processorLoad(sys_info.processor_usage())
        json_b.add_freeDiskSpace(sys_info.drive_space())
        json_b.add_temperature(sys_info.get_temp())
        json_b.add_mac(sys_info.get_macs()[0][1])
        return json_b

    def run(self):
        self.setup()
        while True:
            try:
                self.loop()
                self.last_update = datetime.datetime.now()
            except Exception as ex:
                print(ex.args)
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
