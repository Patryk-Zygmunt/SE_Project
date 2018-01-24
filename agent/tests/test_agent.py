from unittest import TestCase
from unittest.mock import Mock, patch
from agent import DaemonLogger
from collector import CollectorException


class DaemonLoggerTest(TestCase):
    @staticmethod
    def exception_raise():
        raise Exception('test exception')

    @staticmethod
    def collector_exception_raise():
        raise CollectorException('test exception')

    @patch("logging.exception")
    def test_exc_assistant(self, _):
        agent = DaemonLogger('none')
        agent.agentLog = Mock()

        result = agent.exc_assist(lambda x: x - 1, lambda x: x * 10, lambda: 1)
        result2 = agent.exc_assist(lambda x: x - 1, lambda x: self.exception_raise(), lambda: 1)
        result3 = agent.exc_assist(lambda x: x - 1, lambda x: self.collector_exception_raise(), lambda: 1)

        self.assertEqual(9, result)
        self.assertEqual('error', result2)
        self.assertEqual('error', result3)

    @patch("rest.InfoJsonBuilder")
    @patch("collector.SystemDataCollector")
    @patch("collector.JournalLogCollector")
    def test_loop(self, mock1: Mock, mock2, mock3):
        agent = DaemonLogger('none')
        agent.config = Mock()
        agent.agentLog = Mock()
        agent.client = Mock()
        try:
            agent.loop()
        except Exception as ex:
            self.fail(ex.args)
