import subprocess as sub
import re


class SystemData:
    def get_ram_usage(self):
        try:
            ram_data = sub.run(['free', '-m'], stdout=sub.PIPE)
            ram_data.check_returncode()
            total_mem, used_mem = self.__format_ram_str(str(ram_data))
            return (int(total_mem), int(used_mem))
        except:
            print("exep")
            return ("read error occured", "read error occured")

    def __format_ram_str(self, word):
        mem_data = word.split("\\n")[1]
        mem_data = mem_data[4:]
        match_data = re.split(" +", mem_data)
        return (match_data[1], match_data[2])


