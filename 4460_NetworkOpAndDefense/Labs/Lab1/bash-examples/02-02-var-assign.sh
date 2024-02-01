#!/bin/bash
# Using environment variables

# Correct assignment (no spacing)
xyz="123"
xyZ=456
LOC=`pwd`
echo $xyz $xyZ $LOC
echo ---
# Incorrect assignment (generates error)
# xyz = 123

hello=42
echo hello
# Prints the string "hello"

echo $hello
#    ^-------variable reference, prints "42"

echo ${hello}
#    ^-------variable reference, prints "42"

echo ">> hello=\"a b     c    d\""  #
hello="a b     c    d"

# Using double quotes preserves spaces
echo ">> echo \$\"hello\"" #
echo "$hello"

# Not using it causes the removal of duplicate spaces, tabs, newlines
echo ">> echo \$hello"     # 
echo $hello

# Using single quotes interpretes $ literally
echo ">> echo '$hello'"    #
echo '$hello'
echo ---

# Arithmetic -- WRONG 
echo "Arithmetic: variables treated as strings" #
echo ">> x=1; y=\$x+2; echo \$y"
x=1; y=$x+2; echo $y
echo ---

# Arithmetic -- CORRECT
echo "Arithmetic: variables treated as numbers" 
echo ">> ((x=1)); ((y=\$x+2)); echo \$y"
((x=1)); ((y=$x+2)); echo $y
echo ---

# Unsetting a variable
echo ">> x=1; unset x; echo \"|\$x|\""
x=1; unset x; echo "|$x|"
echo ---

