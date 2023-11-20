'''
Version 1.2 (Print)

The exact same as 1.2 but it prints out the board as it fills in. Obviously, this slows the program down, but it's good for demos
Because of the volitile nature of the backtracking algorithm, the 3rd input is a seed that can be used to gaurantee a result
    So far, "3 4 2" is a good choice because it only takes around 30 sec for the demo to complete
'''

import sys, math, random, time, os
import numpy as np

def printBoard(board, length, width):
    maxDigits = int(math.log(length*width, 10)) + 1
    horizontalLine = "-"*((maxDigits) * length * width) + "-"*((width)*(maxDigits+1)*2) + "-"*(width+1)
    totalLength = length*width
    output = horizontalLine + "\n"
    
    for i in range(0, totalLength):
        output += "|"
        
        for j in range(0, totalLength):
            if board[i][j] == 0:
                numDigits = 1
            else:
                numDigits = int(math.log(board[i][j], 10)) + 1
            output += " "*(maxDigits - numDigits + 1) + str(board[i][j])
            
            if (j+1) % length == 0:
                output += " "*(maxDigits) + "|"
                
        if (i+1) % width == 0:
            output += "\n" + horizontalLine
            
        output += "\n"
    
    return output

def boardToTxtFormat(board, length, width):
    output = ""
    maxDigits = int(math.log(length*width, 10)) + 1

    for row in board:
        for col in row:
            if col == 0:
                numDigits = 1
            else:
                numDigits = int(math.log(col, 10)) + 1
            output += (" " * (maxDigits-numDigits + 1)) + str(col)
        output += "\n"
    
    return output



def makeBoard(board, length, width, seed):
    iter = 0
    totalLength = length*width
    valid = False
    numList = [[(k+1) for k in range(totalLength)] for k in range(totalLength)]
    invalidList = [[[] for a in range(totalLength)] for b in range(totalLength)]
    display = ""
    
    i = 0
    while(i < totalLength):
        random.seed(seed[i])
        random.shuffle(numList[i])
        #print("New numList: " + str(numList))
        
        j = 0
        while(j < totalLength):
            iter = iter + 1 
            
            valid = False
            
            for k in range(0, len(numList[i])):
                input = numList[i][k]
                if (checkCol(board, length, width, i, j, input) & checkCell(board, length, width, i, j, input)) & (not(input in invalidList[i][j])):
                    valid = True
                    board[i][j] = numList[i].pop(k)
                    break
            
            if not valid:
                if j == 0:
                    board[i] = [0 for k in range(totalLength)]
                    for k in range(totalLength):
                        invalidList[i][j] = []
                    i -= 1
                    j = totalLength - 1
                    numList[i].append(board[i][j])
                    invalidList[i][j].append(board[i][j])
                    board[i][j] = 0
                else:
                    invalidList[i][j] = []
                    j -= 1
                    numList[i].append(board[i][j])
                    invalidList[i][j].append(board[i][j])
                    board[i][j] = 0
                
            display = ""
            display += "- Iteration #" + str(iter) + " -\n"
            display += "numList: " + str(numList[i]) + "\n"
            display += "(x,y): (" + str(j) + "," + str(i) + ")\n"
            display += "Board: \n"
            display += printBoard(board, length, width) + "\n"
            time.sleep(0.03)
            os.system("cls")
            print(display)
            
            
            
            
            j += 1
            if not valid:
                j -= 1
        if not valid:
            i -= 1
        i += 1
                    
    
        

                    
    
def checkCol(board, length, width, y, x, input):
    for i in range(0, length*width):
        if input == board[i][x]:
            return False
    return True

def checkCell(board, length, width, y, x, input):
    leftWall = int(x/length) * length
    rightWall = leftWall + length
    topWall = int(y/width) * width
    botWall = topWall + width
    
    for i in range(topWall, botWall):
        for j in range(leftWall, rightWall):
            if input == board[i][j]:
                return False
    return True


    

length = 3
width = 4
area = length*width
seed = 2

board = [[0 for i in range(area)] for i in range(area)]
seedList = [(a+1) for a in range(9999)]
random.seed(seed)
random.shuffle(seedList)

tic = time.perf_counter()
makeBoard(board, length, width, seedList)
#printBoard(board, length, width)
toc = time.perf_counter()
print("Ran for " + str((toc - tic)) + " seconds")

file = open("./Demo/DemoBoardSolution.txt", "w")
file.write(boardToTxtFormat(board, length, width))
file.close()
#makeBoard(board)
