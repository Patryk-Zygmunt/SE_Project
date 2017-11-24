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


    #unints - mb
    def ram_usage(self):
        try:
            ram_data = self.__exec_sys_command("free", "-m")
            total_mem, used_mem = self.__format_total_and_used_ram(str(ram_data))
            return int(total_mem), int(used_mem)
        except:
            return "read error occurred", "read error occurred"



    def __format_total_and_used_ram(self, raw_data:str):
        mem_data = raw_data.split("\\n")[1]
        mem_data = mem_data[4:]
        match_data = re.split(" +", mem_data)
        return match_data[1], match_data[2]

    def drive_space(self):
        try:
            raw_drive_data = self.__exec_sys_command("df", "-h")
            raw_drive_data = raw_drive_data.stdout
            return raw_drive_data
        except:
            return "error reading data",  "error reading data"

    def __format_drive_space_data(self, raw_data:str):
        pass
       # match_iter = re.finditer(r"/sd\w+", str(raw_data))

    def processor_usage(self):
        try:
            raw_data = self.__exec_sys_command("top","-bn1")
            user_us, sys_us, unused = self.__format_proc_usage(str(raw_data.stdout))
            return float(user_us), float(sys_us), float(unused)
        except:
            return "error reading"

    def __format_proc_usage(self,raw_data: str):
            """":returns tuple user usage, system usage and unused power"""
            cpu_data = raw_data.split("\\n")[2]
            cpu_data = cpu_data[9:]
            cpu_split = cpu_data.split(" ")
            return cpu_split[0].replace(",","."), cpu_split[3].replace(",","."), cpu_split[8].replace(",", ".")


    def disk_operations_in_progess(self):
            """"zwraca io operacji dyskowych w tej chwili (zwykle 0) nie wiem do końca czy oto chodziło"""
            try:
                file = open("/sys/block/sda/stat","r")
                raw_data = file.read()
                file.close()
                curr_io = self.__format_disk_operations_in_progess(str(raw_data))
                return int(curr_io)
            except:
                print("err")
                return "error reading"

    def __format_disk_operations_in_progess(self,raw_data: str):
            split_data = raw_data.split(" ")
            filtered_data = list(filter(lambda x: x != '', split_data))
            return filtered_data[8]

    def __exec_sys_command(self, command:str, args:str):
            raw_data = sub.run([command, args],stdout=sub.PIPE)
            raw_data.check_returncode()
            return raw_data




