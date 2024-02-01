#!/bin/bash

# Command line argument manipulation

echo ----------------------
echo "\$0 -> Script name (argument #0) = $0"
echo "\$# -> Number of arguments = $#"
echo "\$1 -> Argument #1 = $1"
echo "\$2 -> Argument #2 = $2"
echo "\$3 -> Argument #3 = $3"
echo "\$9 -> Argument #9 = $9"
echo "\${11} -> Argument #11 = ${11}"
echo "\$@ -> All arguments as one string = $@"
echo "\$* -> All arguments as an array = $*"
echo ------------------------
echo "Argument shift" 
shift
echo "$*"
echo "Argument shift" 
shift
echo "$*"
echo "Argument shift" 
shift
echo "$*"
echo "----------------------"

echo "${$#}"
echo "\$_ -> Last argument = $_"
echo "\$_ -> Last argument = $_"
echo "\$_ -> Last argument = $_"

