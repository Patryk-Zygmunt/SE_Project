from unittest import TestCase, mock
import rest
import json


class HTTPConnectionMock:
    def __init__(self, address, port):
        pass

    def close(self):
        pass

    def getresponse(self):
        return 1

    def request(self, *args):
        pass


@mock.patch('rest.HTTPConnection', HTTPConnectionMock)
class ClientTest(TestCase):
    def test_send_info(self):
        client = rest.Client("localhost")
        data = {}
        response = client.send_info(data)

        self.assertEqual(response, 1)


class InfoJsonBuilderTest(TestCase):

    def test_all(self):
        b = rest.InfoJsonBuilder()
        b.add_name('name1')
        b.add_mac('00:5a:b6:6a:bz:8f')
        b.add_temperature(65.12)
        b.add_freeDiskSpace([('n1', 12, 100), ('n2', 12, 100)])
        b.add_processorLoad((12.1, 32.1, 70))
        b.add_ramTaken((50, 100))
        b.add_interfaceIO([('wifi', 12, 12)])
        b.add_iOperSec([('disc_1', 12.2, 21.3)])
        b.add_errLogs([('lis 29 20:42:00', 'rwx-Aspire-VN7-591G', 'pulseaudio[1542]',
                        '[pulseaudio] pid.c: Daemon already running.')])

        actual = b.to_json()

        expected = json.dumps({
            'name': 'name1',
            'mac': '00:5a:b6:6a:bz:8f',
            'temperature': 65.12,
            'freeDiskSpace': [{'name': 'n1', 'used': 12, 'total': 100}, {'name': 'n2', 'used': 12, 'total': 100}],
            'processorLoad': {'user': 12.1, 'system': 32.1, 'unused': 70},
            'ramTaken': {'used': 50, 'total': 100},
            'interfaceIO': [{'name': 'wifi', 'rec': 12, 'trans': 12}],
            'iOperSec': [{'name': 'disc_1', 'read': 12.2, 'write': 21.3}],
            'errLogs': [
                {'date': 'lis 29 20:42:00',
                 'process': 'pulseaudio[1542]',
                 'errorDesc': '[pulseaudio] pid.c: Daemon already running.'
                 },
            ]
        }, sort_keys=True)

        print(expected)
        self.assertEqual(actual, expected)

    def test_add_processorLoad(self):
        load = (123.2, 12.2, 321.1)
        b = rest.InfoJsonBuilder()
        b.add_processorLoad(load)
        actual = b.to_json()

        expected = json.dumps({
            'processorLoad': {
                'user': 123.2,
                'system': 12.2,
                'unused': 321.1
            }}, sort_keys=True)

        self.assertEqual(actual, expected)
