from unittest import TestCase
from collector import JournalLogCollector
from unittest.mock import patch, Mock
import datetime
import json


class JournalLogCollectorTest(TestCase):
    @patch('subprocess.Popen')
    def test_parsing_collected_logs(self, popen_mock):
        """Test JournalLogCollector().collect result"""
        data = b'2017-11-29T20:41:29+0100 rwx-Aspire-VN7-591G kernel: Early memory node ranges\n\
        2017-11-29T20:41:29+0100 rwx-Aspire-VN7-591G kernel:   node   0: [mem 0x0000000000001000-0x000000000006efff]\n\
        2017-11-29T20:41:29+0100 rwx-Aspire-VN7-591G kernel:   node   0: [mem 0x0000000000070000-0x0000000000087fff]\n\
        2017-11-29T20:41:29+0100 rwx-Aspire-VN7-591G kernel:   node   0: [mem 0x0000000000100000-0x00000000954fffff]\n\
        2017-11-29T20:41:29+0100 rwx-Aspire-VN7-591G kernel:   node   0: [mem 0x0000000095e00000-0x000000009c6befff]'

        process_mock = Mock()
        attrs = {'communicate.return_value': (data, 'error')}
        process_mock.configure_mock(**attrs)
        popen_mock.return_value = process_mock

        expected = [
            ('2017-11-29T20:41:29+0100', 'rwx-Aspire-VN7-591G', 'kernel', 'Early memory node ranges'),
            ('2017-11-29T20:41:29+0100', 'rwx-Aspire-VN7-591G', 'kernel',
             '  node   0: [mem 0x0000000000001000-0x000000000006efff]'),
            ('2017-11-29T20:41:29+0100', 'rwx-Aspire-VN7-591G', 'kernel',
             '  node   0: [mem 0x0000000000070000-0x0000000000087fff]'),
            ('2017-11-29T20:41:29+0100', 'rwx-Aspire-VN7-591G', 'kernel',
             '  node   0: [mem 0x0000000000100000-0x00000000954fffff]'),
            ('2017-11-29T20:41:29+0100', 'rwx-Aspire-VN7-591G', 'kernel',
             '  node   0: [mem 0x0000000095e00000-0x000000009c6befff]')]

        actual = JournalLogCollector().collect()
        self.assertEqual(len(actual), 5)
        self.assertEqual(actual, expected)

    def test_args_setting(self):
        j = JournalLogCollector()
        j.set_priority(JournalLogCollector.Priority.ERROR)
        j.set_limit(50)
        j.set_utc()
        j.set_reverse()
        j.set_since_date('2017-11-30 00:34:03.942249')
        # j.set_output('short')
        actual = j.args
        expected = {'--no-pager': None, '-p': 3, '-n': 50, '--utc': None, '-r': None,
                    '--since': "'2017-11-30 00:34:03.942249'", '--output': 'short-iso'}
        self.assertEqual(actual, expected)
