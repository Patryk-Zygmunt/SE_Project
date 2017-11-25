from agent.daemon import Daemon
import os
import sys
import time


class DaemonLogger(Daemon):
    def run(self):
        # just to check if it works
        # TODO redo it when SystemDataCollecotr implemented
        try:
            with open(os.getenv("HOME") + "/daemonfile.txt", "w+") as file:
                file.write("some textfrom")
        except IOError:
            sys.stderr.write("exception in 'run' occured")


if __name__ == "__main__":
    daemon = DaemonLogger('/tmp/daemon-example.pid')
    if len(sys.argv) == 2:
        if 'start' == sys.argv[1]:
            daemon.start()
        elif 'stop' == sys.argv[1]:
            daemon.stop()
        elif 'restart' == sys.argv[1]:
            daemon.restart()
        else:
            print("Unknown command")
            sys.exit(2)
        sys.exit(0)
    else:
        print("usage: %s start|stop|restart" % sys.argv[0])
        sys.exit(2)
