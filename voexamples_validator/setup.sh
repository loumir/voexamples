#!/bin/bash

main_script_path="$PWD/voexamples_validation.py"
alias_name="voexamples-validate"

scripts_dir="$HOME/scripts"
mkdir -p "$scripts_dir"

cp "$main_script_path" "$scripts_dir"
cp -r "$PWD/checker" "$scripts_dir"
cp -r "$PWD/utils" "$scripts_dir"

if [[ ":$PATH:" != *":$scripts_dir:"* ]]; then
    echo "export PATH=\$PATH:$scripts_dir" >> ~/.bashrc
    source ~/.bashrc
fi

if grep -q "alias $alias_name=" ~/.bashrc; then
    echo "The alias '$alias_name' already exists. Skipping alias creation."
else
    # Create an alias for the main script with any number of arguments
    echo "alias $alias_name='python3 $scripts_dir/$(basename "$main_script_path")'" >> ~/.bashrc
    source ~/.bashrc
    echo "Alias '$alias_name' created successfully! You can now use it with any number of arguments."
fi
