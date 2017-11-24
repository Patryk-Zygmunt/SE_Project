from SERVER_DEAMON import Daemon
import os,sys,time


class DaemonLogger(Daemon.Daemon):

    def run(self):
        try:
            #just to check if it works
            #! TODO redo it when SystemDataCollecotr implemented
            file = open("/home/matshec/daemonfile.txt", "w+")
            file.write("some textfrom deamon123")
            file.close()
            time.sleep(100)
        except Exception:
            sys.stderr.write("exception in 'run' occured")



