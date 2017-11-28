from unittest import TestCase
from collector import LogCollector


class LogCollectorTest(TestCase):

    @staticmethod
    def fancy_log_print(logs):
        print(LogCollector.header)
        for log in logs:
            print(log)

    def test_show_syslog(self):
        log = LogCollector()
        self.fancy_log_print(log.collect_syslog())

