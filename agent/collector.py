import subprocess as sub
import re
import time


class SystemDataCollector:

    def get_hostname(self):
        try:
            return str(self.__exec_sys_command('hostname', '-s').stdout, 'utf-8')[:-1]
        except sub.CalledProcessError:
            return 'error'

    def get_macs(self):
        try:
            ps = sub.Popen(('ifconfig', '-a'), stdout=sub.PIPE)
            output = sub.check_output(('awk', "/HWaddr/ {print $1, $5}"), stdin=ps.stdout)
            ps.wait()
            return [tuple(io.split(' ')) for io in str(output, 'utf-8')[:-1].split('\n')]
        except Exception:
            return [('macs cant be read', '')]

    def get_temp(self):
        try:
            temp_file = open("/sys/class/thermal/thermal_zone0/temp", "r")
            temp = temp_file.read()
            temp_file.close()
            return float(int(temp) / 1000)
        except:
            return "error reading temp"

    # unints - mb
    def ram_usage(self):
        try:
            ram_data = self.__exec_sys_command("free", "-m")
            total_mem, used_mem = self.__format_total_and_used_ram(str(ram_data))
            return int(total_mem), int(used_mem)
        except:
            return "read error occurred", "read error occurred"

    def __format_total_and_used_ram(self, raw_data):
        mem_data = raw_data.split("\\n")[1]
        mem_data = mem_data[4:]
        match_data = re.split(" +", mem_data)
        return match_data[1], match_data[2]

    def drive_space(self):
        """:returns list of tuples with name,size,used_size [GB] eg. [(a1,200G,120G),(b2,30M,12M)]"""
        try:
            raw_drive_data = self.__exec_sys_command("df", "-h")
            raw_drive_data = raw_drive_data.stdout
            drive_data = self.__format_drive_space_data(raw_drive_data)
            return drive_data
        except:
            return "error reading data"

    def __format_drive_space_data(self, raw_data_str):
        raw_data_list = raw_data_str.split(b"\n")
        sd_list = list(filter(lambda x: b"/dev/sd" in x, raw_data_list))
        ret_list = []
        for dt in sd_list:
            filtered_spaces = list(filter(lambda x: x != b'', re.split(b' ', dt)))
            name = filtered_spaces[0][5:]
            size = filtered_spaces[1]
            used = filtered_spaces[2]
            ret_list.append((name.decode("utf-8"), size.decode("utf-8"), used.decode("utf-8")))
        return ret_list

    def processor_usage(self):
        try:
            raw_data = self.__exec_sys_command("top", "-bn1")
            user_us, sys_us, unused = self.__format_proc_usage(str(raw_data.stdout))
            return float(user_us), float(sys_us), float(unused)
        except:
            return "error reading"

    def __format_proc_usage(self, raw_data):
        """:returns tuple user usage, system usage and unused power"""
        cpu_data = raw_data.split("\\n")[2]
        cpu_data = cpu_data[9:]
        cpu_split = cpu_data.split(" ")
        return cpu_split[0].replace(",", "."), cpu_split[3].replace(",", "."), cpu_split[8].replace(",", ".")

    def drive_operations(self):
        """:returns list of tuples with name,read/sec,write/sec"""
        try:
            raw_data = self.__exec_sys_command("iostat", "-dx")
            raw_data = raw_data.stdout
            return self.__format_drive_operations(raw_data)
        except:
            return "error occured"

    def __format_drive_operations(self, raw_str):
        split_data = raw_str.split(b"\n")[3:]
        ret_list = []
        split_data = list(filter(lambda x: x != b'', split_data))
        for x in split_data:
            filt_str = list(filter(lambda y: y != b'', re.split(b' ', x)))
            name = filt_str[0]
            r_sec = filt_str[3]
            w_sec = filt_str[4]
            ret_list.append((name.decode("utf-8"), r_sec.decode("utf-8"), w_sec.decode("utf-8")))
        return ret_list

    def interface_load(self):
        """:returns list of tuples with name,rec/sec,trans/sec"""

        def diff(el1, el2):
            n, r1, t1 = el1
            n, r2, t2 = el2
            return (n, r2 - r1, t2 - t1)

        try:
            first_list = self.__get_curr_intf_load()
            time.sleep(1)
            second_list = self.__get_curr_intf_load()
            return list(map(diff, first_list, second_list))
        except:
            return "error"

    def __get_curr_intf_load(self):
        try:
            raw_data = self.__exec_sys_command("netstat", "-i")
            raw_data = raw_data.stdout
            return self.__format_intf_load(raw_data)
        except:
            raise Exception

    def __format_intf_load(self, raw_str):
        split_data = raw_str.split(b"\n")[2:]
        split_data = list(filter(lambda x: x != b'', split_data))
        ret_list = []
        for x in split_data:
            filt_str = list(filter(lambda y: y != b'', re.split(b' ', x)))
            name = filt_str[0]
            received = filt_str[1]
            transmitted = filt_str[7]
            transmitted_int = int(transmitted.decode("utf-8"))
            received_int = int(received.decode("utf-8"))
            ret_list.append((name.decode("utf-8"), received_int, transmitted_int))
        return ret_list

    def __exec_sys_command(self, command, args):
        raw_data = sub.run([command, args], stdout=sub.PIPE)
        raw_data.check_returncode()
        return raw_data

