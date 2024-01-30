#!/bin/bash

# 02-05 Escaping

# Escaping is a method of quoting single characters. The escape (\)
# preceding a character tells the shell to interpret that character
# literally.

# With certain commands and utilities, such as echo and sed, escaping a
# character may have the opposite effect - it can toggle on a special
# meaning for that character.

# Special meanings of certain escaped characters used with echo and sed
#
#  \n   newline
#  \r   return
#  \t   tab
#  \v   vertical tab
#  \b   backspace
#  \a   "alert" (beep or flash)
#  \0xx translates to the octal ASCII equivalent of 0xx


# Escaping a newline.
echo ---------------------
echo "This will print
as two lines."
# This will print
# as two lines.
echo ---
echo "This will print \
as one line."
# This will print as one line.
echo ---

echo "============="
echo "\v\v\v\v"       # Prints \v\v\v\v literally.

# Use the -e option with 'echo' to print escaped characters.
echo "============="
echo "VERTICAL TABS"
echo -e "---\v---\v---\v---\v"    # Prints 4 vertical tabs.
echo "=============="
echo "QUOTATION MARK"
echo -e "\042"        # Prints " (quote, octal ASCII character 42).
echo "=============="

# The $'\X' construct makes the -e option unnecessary.
echo; echo "NEWLINE AND BEEP"
echo $'\n'            # Newline.
echo $'\a'            # Alert (beep).
echo "==============="
echo "QUOTATION MARKS"

# Version 2 and later of Bash permits using the $'\nnn' construct.
# Note that in this case, '\nnn' is an octal value.
echo $'\t \042 \t'    # Quote (") framed by tabs.

# It also works with hexadecimal values, in an $'\xhhh' construct.
echo $'\t \x22 \t'    # Quote (") framed by tabs.

# Assigning ASCII characters to a variable.
echo ---------------------
quote=$'\042'         # " assigned to a variable.
echo "$quote This is a quoted string, $quote and this lies outside the quotes."
echo ---
# Concatenating ASCII chars in a variable.
triple_underline=$'x5F\x5F\x5F' # 5F is hexadecimal code for '_'.
echo "$triple_underline UNDERLINE $triple_underline"
echo ---
ABC=$'\101\102\103\010'           # 101, 102, 103 are octal A, B, C.
echo $ABC
echo; echo
escape=$'\033'                    # 033 is octal for escape.
echo "\"escape\" echoes as $escape"
echo $'A\tB\tC\na\tb\tc'          # 2 rows x 3 cols table with tabs
echo $'11\b22\b33\b44\b5'         # Visibly prints five chacters '12345'
                                  # HOWEVER, if redirected to file, all 13 will show up.
exit 0
