"""
Created on 31-05-2023

author: J. Abid
"""

import os
import sys
from checker.stepfiles_checker import StepFilesChecker
from utils.custom_logs import CustomLogs

class FileChecker:

    def __init__(self, file):
        self.file = file
        self.log = CustomLogs(os.path.basename(file))
        self._check_file()
        self.content = self._get_content()
        self.error_count = 0
        self.warning_count = 0

    def validate(self):
        """
        Validate the file
        """
        self._check_content()
        self.log.info(f"Warnings: {self.warning_count} | Errors: {self.error_count}")

    def _check_file(self):
        """
        Check if the file exists
        """
        if self._get_extension() != "dir":
            if not os.path.isfile(self.file):
                self.log.error("File not found.")
                sys.exit("FileNotFound")

    def _check_content(self):
        """
        Check the content of the file
        """
        file_type = self._get_type()
        if len(self.content) == 0 and self.content != "none" and self._get_extension() != "dir":
            self.log.warning("File should not be empty")
            self.warning_count += 1

        if file_type == "title" or file_type == "uri":
            if len(self.content.split("\n")) > 2:
                self.log.warning("Title file should contain only one line")
                self.warning_count += 1

        if file_type == "index":
            lines = self.content.split("\n")
            for i in range(len(lines)):
                for j in range(i + 1, len(lines)):
                    if lines[i] == lines[j]:
                        self.log.warning("Index file should not contain two identical lines")
                        self.warning_count += 1
                if lines[i] != "":
                    if lines[i] == "index.txt":
                        self.log.error("Index file should not contain itself")
                        self.error_count += 1
                    if lines[i] not in os.listdir(os.path.dirname(self.file)):
                        self.log.error(f"{lines[i]} does not exist in the page directory, please check your "
                                       "index.txt file")
                        self.error_count += 1

            for file in os.listdir(os.path.dirname(self.file)):
                if file != "index.txt" and file != "title.txt" and file != ".DS_Store":
                    if file not in lines:
                        self.log.warning(f"{file} file is not in the index.txt, "
                                         f"consider adding it if you want it to be displayed")
                        self.warning_count += 1

        if file_type == "description" or file_type == "usage":
            if "http" in self.content or "www" in self.content:
                self.log.warning(f"{self._get_type()} file should not contain links, use uri file instead")
                self.warning_count += 1

        if file_type == "image":
            if self._get_extension() != "png" and self._get_extension() != "jpg":
                self.log.error("Image file should be a png or jpg file")
                self.error_count += 1

        if self._get_extension() == "dir":
            if len(os.listdir(self.file)) == 0:
                self.log.warning("Directory should not be empty")
                self.warning_count += 1
            if file_type != "stepquery":
                self.log.error("Directory name should contain stepquery")
                self.error_count += 1

            step_query = StepFilesChecker(self.file)
            step_query.validate()
            self.warning_count += step_query.warning_count
            self.error_count += step_query.error_count

    def _get_type(self):
        """
        Get the type of the file
        """
        types = ["title", "index", "description", "usage", "query", "uri"]

        for t in types:
            if t in self.file:
                if self._get_extension() == "dir" and t == "query":
                    return "stepquery"
                return t
            elif self._get_extension() == "png" \
                    or self._get_extension() == "jpg" \
                    or self._get_extension() == "jpeg" \
                    or self._get_extension() == "gif":
                return "image"

        return "other_uri"

    def _get_extension(self):
        """
        Get the extension of the file
        """
        if os.path.isdir(self.file):
            return "dir"

        if "." in self.file:
            return self.file.split(".")[-1]

        self.log.error("File has no extension")
        self.error_count += 1

        return "none"

    def _get_content(self):
        """
        Get the content of the file
        """
        if self._get_type() != "image" and self._get_type() != "stepquery" and self._get_extension() != "none":
            with open(self.file, "r") as f:
                return f.read()

        return "none"
