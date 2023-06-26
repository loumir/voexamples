"""
Created on 31-05-2023

author: J. Abid
"""

import os
import sys
from checker.file_checker import FileChecker
from utils.custom_logs import CustomLogs


def main():
    """
    Main function
    """

    log = CustomLogs("VOExamples Validator")
    check_arg(log)

    error_count = 0
    warning_count = 0

    log.title(f"Validation of {os.path.basename(os.path.dirname(sys.argv[1]))}")

    if "title.txt" not in os.listdir(sys.argv[1]):
        log.error("Missing title.txt, consider adding one")
        error_count += 1
    if "description.txt" not in os.listdir(sys.argv[1]):
        log.warning(
            "Missing basic description.txt, consider adding one"
            " or extract of description will not be displayed in the page list"
        )
        warning_count += 1

    for file in os.listdir(sys.argv[1]):
        if file not in ["title.txt", "usage.txt", "description.txt", "query.txt"]:
            if "index.txt" not in os.listdir(sys.argv[1]):
                log.error("index.txt file is missing, consider creating one")
                break

    for f in os.listdir(sys.argv[1]):
        if f.count(".") > 1:
            log.warning("Consider using '.' only for extension and not for file name")
            warning_count += 1
        to_check = FileChecker(os.path.abspath(os.path.join(sys.argv[1], f)))
        to_check.validate()
        error_count += to_check.error_count
        warning_count += to_check.warning_count

    if error_count == 0 and warning_count == 0:
        log.success("Validation totally successful")
    else:
        log.warning(
            f"Validation failed with {warning_count} warnings and {error_count} errors"
        )


def check_arg(log):
    if len(sys.argv) != 2:
        log.error("Too many or none arguments")
        log.info("Please provide the path to the page content directory")
        sys.exit("TooManyArguments")

    if not os.path.isdir(sys.argv[1]):
        log.error("Please provide a directory as argument")
        log.info("Please provide the path to the page content directory")
        sys.exit("NotADirectory")


if __name__ == "__main__":
    main()
