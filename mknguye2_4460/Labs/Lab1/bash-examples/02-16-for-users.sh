#!/bin/bash

# CSCI 4460
# 02-16 Use for loop to enumerate all users on the system

PASSWORD_FILE=/etc/passwd
n=1            # User number

for name in $(awk 'BEGIN{FS=":"}{print $1}' < "$PASSWORD_FILE" )
# Field separator = :     ^^^^^^
# Print first field              ^^^^^^^^
# Get input from password file               ^^^^^^^^^^^^^^^^^
do
   echo "USER #$n = $name"
   let "n += 1"
done
# Output:
#  USER #1 = root
#  USER #2 = bin
#  USER #3 = daemon
#  ...
#  USER #30 = bozo
exit 0
