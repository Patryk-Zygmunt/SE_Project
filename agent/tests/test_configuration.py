from unittest import TestCase
from unittest.mock import Mock, patch, mock_open
from configuration import Config, LogsConfig
import json


class ConfigTest(TestCase):

    @patch("os.path.getatime")
    def test_init(self, _):
        dict_ = {
            "config_update_delay": 10,
            "server": {
                "ip": "localhost",
                "port": 8081
            },
            "send_frequency": 10,
            "send_agent_errors": False,
            "sys_logs": {
                "send": True,
                "limit": 200,
                "reverse": True,
                "priority": "ERROR"
            }
        }
        mock = mock_open(read_data=json.dumps(dict_))
        with patch("configuration.open", mock):
            conf = Config()
            self.assertEqual(10, conf.get_send_frequency())
            self.assertEqual('localhost', conf.get_server_ip())
            self.assertEqual(8081, conf.get_server_port())
            self.assertEqual(dict_['sys_logs'], conf.logs_config.__dict__)

    @patch("os.path.getatime")
    @patch("builtins.open", new_callable=mock_open, read_data='{\"config_update_delay\": 1, \"sys_logs\": {}}')
    def test_config_update(self, _, mock: Mock):
        mock.return_value = 2
        conf = Config()
        mock.return_value = 10
        self.assertEqual(True, conf.check_config_update())
        conf.update_config()
        self.assertEqual(10, conf.last_modification)
