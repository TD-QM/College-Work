#!/bin/bash

# CSCI 4460
# 02-13 Retrieve info for a list of known files

FILES="/bin/ls
/usr/bin/yelp
/usr/sbin/pwck
/usr/sbin/chroot
/usr/bin/fakefile
/bin/netstat"

echo
for file in $FILES
do
   echo "File: $file"
   if [ ! -e "$file" ]       # Check if file exists.
   then
     echo "$file does not exist."; echo
     continue                # On to next.
    fi
   ls -l $file | awk '{ print $9 "         file size: " $5 }' # Print 2 fields.
   whatis `basename $file`   # File info.
   # Note that the whatis database needs to have been set up for this to work.
   # To do this, as root run /usr/bin/makewhatis.
   echo
done

exit 0

