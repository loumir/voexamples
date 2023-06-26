"""
Created on 31-05-2023

author: J. Abid
"""


class BColors:
    """
    classdocs
    """
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

class CustomLogs:
    """
    classdocs
    """
    def __init__(self, file):
        self.file = file

    def info(self, txt):
        print(f"{BColors.OKCYAN}[ INFO - {self.file} ]: {txt} {BColors.ENDC}")

    def warning(self, txt):
        print(f"{BColors.WARNING}[ WARNING - {self.file} ]: {txt} {BColors.ENDC}")

    def error(self, txt):
        print(f"{BColors.FAIL}[ ERROR - {self.file} ]: {txt} {BColors.ENDC}")

    def success(self, txt):
        print(f"{BColors.BOLD}{BColors.OKGREEN}[ SUCCESS - {self.file} ]: {txt} {BColors.ENDC}")

    def title(self, txt):
        print(f"{BColors.BOLD}{BColors.HEADER}[ INFO - {self.file} ]: {txt} {BColors.ENDC}")

