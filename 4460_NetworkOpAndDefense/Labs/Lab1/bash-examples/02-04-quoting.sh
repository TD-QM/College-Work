#!/bin/bash

# 02-04 Quoting

ls -al *[Vv]*                          # Show all files containing 'v' or 'V'
ls -al '*[Vv]*'                        # Show all files containing '*[Vv]*'

echo ---------------------------------
grep '[Cc]ompress*' rfc1950.txt        # Quotes protect argument from expansion by shell

echo ---------------------------------
echo $(ls -l)                          # echo eliminates 'extra' delimeters
echo "$(ls -l)"                        # Double quotes protect them

echo ---------------------------------
list="one two three"
for a in $list                         # Splits the variable in parts at whitespace.
do
   echo "$a"
done
# one
# two
# three
echo ---
for a in "$list"     # Double quotes preserve whitespace in a single variable.
do
   echo "$a"
done
# one two three
