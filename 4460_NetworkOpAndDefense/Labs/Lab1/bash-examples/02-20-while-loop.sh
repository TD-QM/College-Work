#!/bin/bash

# CSCI 4460
# 02-21 While loop examples.

var0=0
LIMIT=10
while [ "$var0" -lt "$LIMIT" ]
#       ^                   ^
# Spaces, because these are "test-brackets" . . .
do
   echo -n "$var0 "       # -n suppresses newline.
   #             ^          Space, to separate printed out numbers.
  var0=`expr $var0 + 1` # var0=$(($var0+1)) also works.
                        # var0=$((var0 + 1)) also works.
                        # let "var0 += 1"    also works.
done                    # Various other methods also work.


echo ------

while [ "$var1" != "end" ]      # while test "$var1" != "end"
do
   echo "Input variable #1 (end to exit) "
   read var1                    # Not 'read $var1' (why?).
   echo "variable #1 = $var1"   # Need quotes because of "#" . . .
   # If input is 'end', echoes it here.
   # Does not test for termination condition until top of loop.
   echo
done
exit 0
