#!/bin/bash

# CSCI 4460
# 02-18 Generate a list of symbolic links and save it

OUTFILE=symlinks.list                         # save file
directory=${1-`pwd`}
# Defaults to current working directory,
#+ if not otherwise specified.
echo "symbolic links in directory \"$directory\"" > "$OUTFILE"
echo "---------------------------" >> "$OUTFILE"
for file in "$( find $directory -type l )"    # -type l = symbolic links
do
   echo "$file"
done | sort >> "$OUTFILE"                     # stdout of loop
#            ^^^^^^^^^^^^^                       redirected to save file.
exit 0
