from unittest import TestCase
from collector import JournalLogCollector, SystemDataCollector
from unittest.mock import patch, Mock


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


class SystemDataCollectorTest(TestCase):
    @patch('subprocess.run')
    def test_collecting_processor_usage_data1(self, run_mock):
        data = b'top - 14:12:47 up 17:31,  1 user,  load average: 0,87, 0,78, 0,74\n\
        Tasks: 316 total,   1 running, 315 sleeping,   0 stopped,   0 zombie\n\
        %Cpu(s):  3,0 us,  0,6 sy,  0,0 ni, 95,8 id,  0,6 wa,  0,0 hi,  0,0 s\n\
        KiB Mem : 16350924 total,   293252 free, 10863488 used,  5194184 buff\n\
        KiB Swap:   976892 total,   976892 free,        0 used.  4201472 avai\n\
        \n\
          PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+\n\
        10242 rwx       20   0 6817428 1,068g  39452 S  12,5  6,9  47:18.70'

        check_valuemock = Mock()
        attrs = {'check_returncode': lambda: None, 'stdout': data}
        check_valuemock.configure_mock(**attrs)
        run_mock.return_value = check_valuemock

        actual = SystemDataCollector().processor_usage()
        expected = (3.0, 0.6, 0.0)

        self.assertEqual(actual, expected)

    @patch('subprocess.run')
    def test_collecting_processor_usage_data2(self, run_mock):
        data = b'top - 14:51:18 up 19 days,  1:16,  4 users,  load average: 0.01, 0.02\n\
        Tasks: 122 total,   1 running, 121 sleeping,   0 stopped,   0 zombie\n\
        %Cpu(s):  0.2%us,  0.3%sy,  0.0%ni, 99.3%id,  0.0%wa,  0.0%hi,  0.0%s\n\
        KiB Mem :  1012092 total,   517016 free,    70076 used,   425000 buff\n\
        KiB Swap:        0 total,        0 free,        0 used.   888268 avai\n\
        \n\
          PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+\n\
        21581 root      20   0   39576   3300   2804 R   6.2  0.3   0:00.01\n\
            1 root      20   0   37736   5068   3272 S   0.0  0.5   0:29.67\n\
            2 root      20   0       0      0      0 S   0.0  0.0   0:00.02'

        check_valuemock = Mock()
        attrs = {'check_returncode': lambda: None, 'stdout': data}
        check_valuemock.configure_mock(**attrs)
        run_mock.return_value = check_valuemock

        actual = SystemDataCollector().processor_usage()
        expected = (0.2, 0.3, 0.0)

        self.assertEqual(actual, expected)
