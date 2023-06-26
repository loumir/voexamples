"""
Created on 31-05-2023

author: J. Abid
"""

import os
import sys
from utils.custom_logs import CustomLogs


class StepFilesChecker:

    def __init__(self, step_dir):
        self.dir = step_dir
        self._check_dir()
        self.files = self._get_files()
        self.log = None
        self.warning_count = 0
        self.error_count = 0

    def validate(self):
        """
        Validate the directory
        """
        for f in self.files:
            self.log = CustomLogs(f"{os.path.basename(self.dir)}/{os.path.basename(f)}")
            self._get_extension(f)
            self._check_content(f)
            self.log.info(f"Warnings: {self.warning_count} | Errors: {self.error_count}")

    def _check_content(self, file):
        """
        Check the content of the file
        """
        file_type = self._get_type(file)
        file_content = self._get_content(file)

        if file_type == "index":
            lines = file_content.split("\n")
            for i in range(len(lines)):
                for j in range(i + 1, len(lines)):
                    if lines[i] == lines[j]:
                        self.log.warning("Index file should not contain two identical lines")
                        self.warning_count += 1
                if lines[i] == "index.txt":
                    self.log.error("Index file should not contain itself")
                    self.error_count += 1
                if "description" not in lines[i] and "query" not in lines[i]:
                    self.log.error(f"Index file should not contain any file except description and query files, remove {lines[i]}")
                    self.error_count += 1
            if lines in self.files:
                self.log.error("Index file should contain all the files in the directory except itself and the title "
                               "file")
                self.error_count += 1

            for file in self.files:
                if file != "index.txt" and file not in lines:
                    self.log.warning(f"{file} file is not in the index.txt, "
                                     f"consider adding it if you want it to be displayed")
                    self.warning_count += 1

        if file_type == "description":
            if "http" in file_content or "www" in file_content:
                self.log.warning(self._get_type(file), " file should not contain links")
                self.warning_count += 1

    def _check_dir(self):
        """
        Check if the directory exists
        """
        if not os.path.isdir(self.dir):
            self.log.error("Directory not found")
            self.error_count += 1
            sys.exit("DirectoryNotFound")

    def _get_type(self, file):
        """
        Get the type of the file
        """
        types = ["index", "description", "query"]

        for t in types:
            if t in file:
                return t
        self.log.error("File type not recognized: " + file)
        self.error_count += 1
        return "none"

    def _get_extension(self, file):
        """
            Get the extension of the file
            """
        if os.path.isdir(file):
            self.log.error("Cannot have directory in stepquery")
            self.error_count += 1

        if "." in file:
            return file.split(".")[-1]

        self.log.error("File has no extension")
        self.error_count += 1

    def _get_files(self):
        """
        Get the files in the directory
        """
        return os.listdir(self.dir)

    def _get_content(self, file):
        """
        Get the content of the file
        """
        if os.path.isdir(file):
            return ""

        with open(os.path.abspath(os.path.join(self.dir, file)), "r") as f:
            return f.read()
