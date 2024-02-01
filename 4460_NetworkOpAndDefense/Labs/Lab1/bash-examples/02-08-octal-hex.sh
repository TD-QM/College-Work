#!/bin/bash

# 02-08 Octal and hexadecimal evaluation in [] and [[]]

decimal=15
octal=017      # 15 (decimal)
hex=0x0f       # 15 (decimal)

if [ "$decimal" -eq "$octal" ]
then
   echo "$decimal equals $octal"
else
   echo "$decimal is not equal to $octal"   # 15 is not equal to 017
fi                                          # Doesn't evaluate within [ single brackets ]!

echo ---
if [[ "$decimal" -eq "$octal" ]]
then
   echo "$decimal equals $octal"
else
  echo "$decimal is not equal to $octal"
fi                                         # Evaluates within [[ double brackets ]]!

echo ---
if [[ "$decimal" -eq "$hex" ]]
then
  echo "$decimal equals $hex"
else
  echo "$decimal is not equal to $hex"
fi                                         # [[ $hexadecimal ]] also evaluates!
