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


# TODO testy kolektora :) :#get_hostname,get_temp, interface_load, exec sysc_com
class SystemDataCollectorTest(TestCase):

    def build_run_ret_val(self, data):
        check_valuemock = Mock()
        attrs = {'check_returncode': lambda: None, 'stdout': data}
        check_valuemock.configure_mock(**attrs)
        return check_valuemock

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

        run_mock.return_value = self.build_run_ret_val(data)

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

        run_mock.return_value = self.build_run_ret_val(data)

        actual = SystemDataCollector().processor_usage()
        expected = (0.2, 0.3, 0.0)

        self.assertEqual(actual, expected)

    @patch('subprocess.run')
    def test_collecting_macs(self, run_mock):
        data = \
            b'1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1\n\
                link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00\n\
            2: enp0s3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000\n\
                link/ether 08:00:27:26:cb:d6 brd ff:ff:ff:ff:ff:ff'

        run_mock.return_value = self.build_run_ret_val(data)

        expected = [('enp0s3', '08:00:27:26:cb:d6')]
        actual = SystemDataCollector().get_macs()

        self.assertEqual(expected, actual)

    @patch('subprocess.run')
    def test_collecting_ram_usage(self, run_mock):
        data = b'              total        used        free      shared  buff/cache   available\n\
            Mem:           7864        4004        1422         273        2436        3207\n\
            Swap:          8072          75        7997'
        run_mock.return_value = self.build_run_ret_val(data)
        expected = (7864, 4004)
        actual = SystemDataCollector().ram_usage()
        self.assertEqual(expected, actual)

    @patch('subprocess.run')
    def test_collecting_drive_space(self, run_mock):
        data = b'Filesystem      Size  Used Avail Use% Mounted on\n\
            udev            3,9G     0  3,9G   0% /dev\n\
            tmpfs           787M  9,7M  777M   2% /run\n\
            /dev/sda2       212G  157G   44G  79% /\n\
            tmpfs           3,9G   31M  3,9G   1% /dev/shm\n\
            tmpfs           5,0M  4,0K  5,0M   1% /run/lock\n\
            tmpfs           3,9G     0  3,9G   0% /sys/fs/cgroup\n\
            /dev/sda1       511M  3,4M  508M   1% /boot/efi\n\
            tmpfs           787M   76K  787M   1% /run/user/1000'
        run_mock.return_value = self.build_run_ret_val(data)
        expected = [('sda2', 217088.0, 160768.0), ('sda1', 511.0, 3.4)]
        actual = SystemDataCollector().drive_space()
        self.assertEqual(expected, actual)

    @patch('subprocess.run')
    def test_collector_drive_operations(self, run_mock):
        data = b'Linux 4.9.0-040900-generic (matshec-Inspiron-5547) 	05.01.2018 	_x86_64_	(4 CPU)\n\
        \n\
        Device:         rrqm/s   wrqm/s     r/s     w/s    rkB/s    wkB/s avgrq-sz avgqu-sz   await r_await w_await  svctm  %util\n\
        sda               0,15     2,53    5,39    1,98   105,95   198,79    82,70     0,01    1,99    0,39    6,32   0,26   0,19\n'
        run_mock.return_value = self.build_run_ret_val(data)
        expected = [('sda', 5.39, 1.98)]
        actual = SystemDataCollector().drive_operations()
        self.assertEqual(expected, actual)
