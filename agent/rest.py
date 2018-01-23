from http.client import HTTPConnection
import json
import sys


class InfoJsonBuilder:
    """
    Buiilds JSON object from collected system data
    Attributes: data(dict) dictionary where information about system load is stored it should be bulit with class methods

    """

    def __init__(self):
        self.data = {}

    def add_name(self, value):
        """"adds name of the machine to json, which is name of the host
        :param value: name of the host machine"""
        self.data['name'] = value
        return self

    def add_mac(self, value):
        """"adds mac address to json
        :param value: mac address"""
        self.__add('mac', value)
        return self

    def add_processor(self, value):
        """"adds processor usage to json"""
        data = {'user': value[0], 'system': value[1], 'unused': value[2]}
        self.__add('processor', data)
        return self

    def add_ram(self, value):
        """"adds ram """
        ram = {'used': value[1], 'total': value[0]}
        self.__add('ram', ram)
        return self

    def add_discs_space(self, values):
        data = [{'name': tup[0], 'used': tup[2], 'total': tup[1]} for tup in values]
        self.__add('discs', data)
        return self

    def add_disc_operations(self, values):
        data = [{'name': tup[0], 'read': tup[1], 'write': tup[2]} for tup in values]
        self.__add('operations', data)
        return self

    def add_temperature(self, value):
        self.__add('temperature', value)
        return self

    def add_io_interface(self, values):
        data = [{'name': tup[0], 'rec': tup[1], 'trans': tup[2]} for tup in values]
        self.__add('ioInterfaces', data)
        return self

    def add_logs(self, values):
        if len(values) > 0:
            data = [{'date': tup[0], 'process': tup[2], 'errorDesc': tup[3]} for tup in values]
            self.__add('logs', data)
        return self

    def to_json(self):
        return json.dumps(self.data, sort_keys=True)

    def __add(self, key, val):
        self.data[key] = val


def Post(fun):
    """function decorator setting POST communication method for send functions"""
    def get_args(self, data):
        return fun(self, 'POST', data)

    return get_args


def Get(fun):
    """function decorator setting GET communication method for send functions"""
    def get_args(self):
        return fun(self, 'GET')

    return get_args


def Path(path):
    """function decorator setting  url path for send functions"""
    def get_function(fun):
        def get_fun_args(self, method, data):
            return fun(self, method, path, data)

        return get_fun_args

    return get_function


class Client:
    """"This class when initialized with configuration  object holds methods to send collected data to server"""

    def __init__(self, config):
        self.config = config

    @Post
    @Path('/api/agent/addInfo')
    def send_info(self, method, path, data):
        """"sends collected information to proper server url using appropriate headers
        :param method: POST or GET
        :param path: url
        :param data: actuall data in the JSON format
        :return response code from server"""
        headers = {"Content-type": "application/json", "Accept": "text/plain"}
        response = self.send(path=path, method=method, headers=headers, data=data)
        return response

    def send(self, method, path, data, headers):
        """"Creates connection with server and sends data,more generic method than send_info
        :param method: POST or GET
        :param path: url
        :param data: actuall data in the JSON format
        :param headers: connection headers
        :return response code from server
        :raise Exception with message connecton refuse. Server not available"""
        try:
            conn = HTTPConnection(self.config.get_server_ip(), self.config.get_server_port())
            conn.request(method, path, data, headers)
            response = conn.getresponse()
            conn.close()
            return response
        except ConnectionRefusedError as ex:
            raise Exception('connection refuse. Server not available - {}:{} '.format(self.config.get_server_ip(), str(
                self.config.get_server_port())), ex)
