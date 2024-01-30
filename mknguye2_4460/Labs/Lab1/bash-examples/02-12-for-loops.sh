#!/bin/bash

# 02-12 Intro for loops

echo "--- for loop version 1 ---"
for planet in Mercury Venus Earth Mars Jupiter Saturn Uranus Neptune Pluto
    do
        echo $planet # Each planet on a separate line.
    done

echo "--- for loop ver 2 ---"
for planet in "Mercury Venus Earth Mars Jupiter Saturn Uranus Neptune Pluto"
     # All planets on same line.
     # Entire 'list' enclosed in quotes creates a single variable.
     # Why? Whitespace incorporated into the variable.
do
   echo $planet
done

echo "--- for loop ver 3 ---"
for planet in `echo "Mercury Venus Earth Mars Jupiter Saturn Uranus Neptune Pluto"`
     # Use echo to parse string into and array.
do
   echo $planet
done

echo "--- for loop ver 4 ---"
# Associate the name of each planet with its distance from the sun.
for planet in "Mercury 36" "Venus 67" "Earth 93" "Mars 142" "Jupiter 483"
do
   set -- $planet # Parses variable "planet"
                   #+ and sets positional parameters.
   # The "--" prevents nasty surprises if $planet is null or
   # begins with a dash.
   echo "$1               $2,000,000 miles from the sun"
   #-------two  tabs---concatenate zeroes onto parameter $2
done

exit 0

