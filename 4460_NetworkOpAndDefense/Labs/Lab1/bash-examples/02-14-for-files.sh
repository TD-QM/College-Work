#!/bin/bash

# CSCI 4460
# 02-14 for loop using file filename expansion (a.k.a. "globbing")

for file in *
#           ^ Bash performs filename expansion
#             on expressions that globbing recognizes.
do
   ls -l "$file" # Lists all files in $PWD (current directory).
   # Recall that the wild card character "*" matches every filename,
   # however, in "globbing," it doesn't match dot-files.
   # If the pattern matches no file, it is expanded to itself.
done

echo ---
for file in [jx]*
do
   rm -f $file    # Removes only files beginning with "j" or "x" in $PWD.
   echo "Removed file \"$file\"".
done

echo ---

exit 0
