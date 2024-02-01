#!/bin/bash

# CSCI 4460
# 02-17 Use function invocation to generate loop list

generate_list ()
{
   echo "one two three"
}
for word in $(generate_list) # Let "word" grab output of function.
do
   echo "$word"
done
# Output:
#   one
#   two
#   three

exit 0
