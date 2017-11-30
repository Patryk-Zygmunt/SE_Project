from unittest import TestCase, mock
import rest
import json


class InfoJsonBuilderTest(TestCase):
    def test_all(self):
        b = rest.InfoJsonBuilder()
        b.add_name('name1')
        b.add_mac('00:5a:b6:6a:bz:8f')
        b.add_temperature(65.12)
        b.add_discs_space([('n1', 100, 12), ('n2', 100, 12)])
        b.add_processor((12.1, 32.1, 70))
        b.add_ram((100, 50))
        b.add_io_interface([('wifi', 12, 12)])
        b.add_disc_operations([('disc_1', 12.2, 21.3)])
        b.add_logs([('lis 29 20:42:00', 'rwx-Aspire-VN7-591G', 'pulseaudio[1542]',
                        '[pulseaudio] pid.c: Daemon already running.')])

        actual = b.to_json()

        expected = json.dumps({
            'name': 'name1',
            'mac': '00:5a:b6:6a:bz:8f',
            'temperature': 65.12,
            'discs': [{'name': 'n1', 'used': 12, 'total': 100}, {'name': 'n2', 'used': 12, 'total': 100}],
            'processor': {'user': 12.1, 'system': 32.1, 'unused': 70},
            'ram': {'used': 50, 'total': 100},
            'ioInterface': [{'name': 'wifi', 'rec': 12, 'trans': 12}],
            'operations': [{'name': 'disc_1', 'read': 12.2, 'write': 21.3}],
            'logs': [
                {'date': 'lis 29 20:42:00',
                 'process': 'pulseaudio[1542]',
                 'errorDesc': '[pulseaudio] pid.c: Daemon already running.'
                 },
            ]
        }, sort_keys=True)
        self.maxDiff = None
        self.assertEqual(actual, expected)

    def test_add_processorLoad(self):
        load = (123.2, 12.2, 321.1)
        b = rest.InfoJsonBuilder()
        b.add_processor(load)
        actual = b.to_json()

        expected = json.dumps({
            'processor': {
                'user': 123.2,
                'system': 12.2,
                'unused': 321.1
            }}, sort_keys=True)

        self.assertEqual(actual, expected)
