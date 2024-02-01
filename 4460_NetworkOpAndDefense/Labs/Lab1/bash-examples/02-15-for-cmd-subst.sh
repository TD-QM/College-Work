#!/bin/bash

# CSCI 4460
# 02-15 for loop using command substitution to create list

NUMBERS="9 7 3 8 37.53"
for number in `echo $NUMBERS`  # for number in 9 7 3 8 37.53
do
   echo -ne "$number\t"
done

exit 0
