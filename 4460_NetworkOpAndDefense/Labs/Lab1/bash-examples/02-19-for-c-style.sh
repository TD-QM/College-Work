#!/bin/bash

# CSCI 4460
# 02-20 Five ways to count up to 10.

echo " --- Standard syntax ---"
for i in 1 2 3 4 5 6 7 8 9 10
do
    echo -n "$i "
done

echo -e "\n--- seq syntax ---"
for i in `seq 10`
do
    echo -n "$i "
done

echo -e "\n--- Using brace expansion ---"
for i in {1..10}
do
    echo -n "$i "
done

echo -e "\n--- C-style syntax ---"
LIMIT=10
for ((i=1; i <= LIMIT ; i++))  # Double parentheses, and "LIMIT" with no "$".
do
   echo -n "$i "
done

echo -e "\n--- C-style w/ comma operator ---"
for ((i=1, j=1; i <= LIMIT ; i++, j++))
do
   echo -n "$i-$j "
done
echo

exit 0
