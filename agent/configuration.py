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

    def __init__(self, path='config.json'):
        super().__init__(daemon=False)
        self.path = path
        self.data = None
        self.last_modification = None
        self.sleep_time = None
        self.logs_config = None
        self.update_config()

    def run(self):
        while True:
            if self.check_config_update():
                self.update_config()
            time.sleep(self.sleep_time)

    def update_config(self):
        logging.debug("Config updated!")
        self.data = self.load_config()
        self.last_modification = os.path.getatime(self.path)
        self.sleep_time = self.data['config_update_delay']
        self.logs_config = LogsConfig(self.data['sys_logs'])

    def check_config_update(self):
        return self.last_modification < os.path.getatime(self.path)

    def load_config(self):
        with open(self.path, 'r') as file:
            return json.load(file)

    @exception_assistant
    def get_server_ip(self):
        return self.data['server']['ip']

    @exception_assistant
    def get_server_port(self):
        return self.data['server']['port']

    @exception_assistant
    def get_send_frequency(self):
        return self.data['send_frequency']
