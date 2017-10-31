import subprocess as sub
import re


class SystemDataCollector:

    def get_temp(self):
        try:
            temp_file = open("/sys/class/thermal/thermal_zone0/temp","r")
            temp = temp_file.read()
            temp_file.close()
            return float(int(temp)/1000)
        except:
            return "error reading temp"



    def __get_ram_usage(self):
        try:
            ram_data = sub.run(['free', '-m'], stdout=sub.PIPE)
            ram_data.check_returncode()
            total_mem, used_mem = self.__get_total_and_used_ram(str(ram_data))
            return int(total_mem), int(used_mem)
        except:

            return "read error occurred", "read error occurred"

    def __get_total_and_used_ram(self, raw_data):
        mem_data = raw_data.split("\\n")[1]
        mem_data = mem_data[4:]
        match_data = re.split(" +", mem_data)
        return match_data[1], match_data[2]





