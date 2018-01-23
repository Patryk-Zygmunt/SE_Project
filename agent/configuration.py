import os
import json
import time
from threading import Thread
import logging


def exception_assistant(func):
    def h_args(*args):
        try:
            return func(*args)
        except Exception as ex:
            raise ConfigException("Exception in function: {}".format(func.__name__), ex)

    return h_args


class ConfigException(Exception):
    pass


class LogsConfig:
    def __init__(self, data):
        self.__dict__ = data


class Config(Thread):
    """"contains most recent configuration state"""

    def __init__(self, path='config.json'):
        super().__init__(daemon=False)
        self.path = path
        self.data = None
        self.last_modification = None
        self.sleep_time = None
        self.logs_config = None
        self.update_config()

    def run(self):
        """"check in  loop if the configuration has been updated and if so update object state"""
        while True:
            if self.check_config_update():
                self.update_config()
            time.sleep(self.sleep_time)

    def update_config(self):
        """"updates configuration of agent, upadate modification time"""
        logging.debug("Config updated!")
        self.data = self.load_config()
        self.last_modification = os.path.getatime(self.path)
        self.sleep_time = self.data['config_update_delay']
        self.logs_config = LogsConfig(self.data['sys_logs'])

    def check_config_update(self):
        """"check if configuration has been modified"""
        return self.last_modification < os.path.getatime(self.path)

    def load_config(self):
        """"load configuration from JSON file in the previously initialized path"""
        return json.load(open(self.path, 'r'))

    @exception_assistant
    def get_server_ip(self):
        return self.data['server']['ip']

    @exception_assistant
    def get_server_port(self):
        return self.data['server']['port']

    @exception_assistant
    def get_send_frequency(self):
        return self.data['send_frequency']


if __name__ == "__main__":
    config = Config()
    print(config.data)
