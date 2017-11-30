from http.client import HTTPConnection
import json
import sys


class InfoJsonBuilder:
    def __init__(self):
        self.data = {}

    def add_name(self, value):
        self.data['name'] = value
        return self

    def add_mac(self, value):
        self.__add('mac', value)
        return self

    def add_processor(self, value):
        data = {'user': value[0], 'system': value[1], 'unused': value[2]}
        self.__add('processor', data)
        return self

    def add_ram(self, value):
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
        data = [{'date': tup[0], 'process': tup[2], 'errorDesc': tup[3]} for tup in values]
        self.__add('logs', data)
        return self

    def to_json(self):
        return json.dumps(self.data, sort_keys=True)

    def __add(self, key, val):
        self.data[key] = val


def Post(fun):
    def get_args(self, data):
        return fun(self, 'POST', data)

    return get_args


def Get(fun):
    def get_args(self):
        return fun(self, 'GET')

    return get_args


def Path(path):
    def get_function(fun):
        def get_fun_args(self, method, data):
            return fun(self, method, path, data)

        return get_fun_args

    return get_function


class Client:
    def __init__(self, address: str, port=80):
        self.address = address
        self.port = port

    @Post
    @Path('/api/agent/addInfo')
    def send_info(self, method, path, data):
        headers = {"Content-type": "application/json", "Accept": "text/plain"}
        response = self.send(path=path, method=method, headers=headers, data=data)
        return response

    def send(self, method, path, data, headers):
        try:
            conn = HTTPConnection(self.address, self.port)
            conn.request(method, path, data, headers)
            response = conn.getresponse()
            conn.close()
            return response
        except ConnectionRefusedError as ex:
            sys.stderr.write('connection refuse. Server not available - {}:{} '.format(self.address, str(self.port)))
            raise Exception('connection refuse. Server not available - {}:{} '.format(self.address, str(self.port)))


if __name__ == '__main__':
    print(Client('localhost', 8080).send_info(json.dumps({})))
