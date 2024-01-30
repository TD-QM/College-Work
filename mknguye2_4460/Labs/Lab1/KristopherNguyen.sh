#!/bin/bash


rm -r output
mkdir output

rm "./outputFile"
touch "./outputFile"

wget -P ./output "https://ayasinnur.com/uno4460/fileNames"
fileNameDir="./output/fileNames"

while read fileNames
do
	wget -P ./output "https://ayasinnur.com/uno4460/$fileNames.tar.gz"

	rm -r "./output/$fileNames"
	mkdir "./output/$fileNames"
	tar -xvf "./output/$fileNames.tar.gz" -C "./output/$fileNames/"

	for file in "./output/$fileNames/*"
	do
		cat < $file >> "./outputFile"
	done

done < "./$fileNameDir"
