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

    def add_processorLoad(self, value):
        data = {'user': value[0], 'system': value[1], 'unused': value[2]}
        self.__add('processorLoad', data)
        return self

    def add_ramTaken(self, value):
        ram = {'used': value[0], 'total': value[1]}
        self.__add('ramTaken', ram)
        return self

    def add_freeDiskSpace(self, values):
        data = [{'name': tup[0], 'used': tup[1], 'total': tup[2]} for tup in values]
        self.__add('freeDiskSpace', data)
        return self

    def add_iOperSec(self, values):
        data = [{'name': tup[0], 'read': tup[1], 'write': tup[2]} for tup in values]
        self.__add('iOperSec', data)
        return self

    def add_temperature(self, value):
        self.__add('temperature', value)
        return self

    def add_interfaceIO(self, values):
        data = [{'name': tup[0], 'rec': tup[1], 'trans': tup[2]} for tup in values]
        self.__add('interfaceIO', data)
        return self

    def add_errLogs(self, values):
        data = [{'date': tup[0], 'process': tup[2], 'errorDesc': tup[3]} for tup in values]
        self.__add('errLogs', data)
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
