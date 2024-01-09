'''
Version 1.2 (Print)

The exact same as 1.2 but it prints out the board as it fills in. Obviously, this slows the program down, but it's good for demos
Because of the volitile nature of the backtracking algorithm, the 3rd input is a seed that can be used to gaurantee a result
    So far, "3 4 2" is a good choice because it only takes around 30 sec for the demo to complete
'''

import sys, math, random, time, os
import numpy as np

def boardToString(board, length, width):
    maxDigits = int(math.log(length*width, 10)) + 1
    horizontalLine = "-"*((maxDigits+1) * length * width) + "-"*((width)*(maxDigits)) + "-"*(width+1)
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
    # For debugging
    iter = 0

    # Valid Number Boolean
    valid = False
    
    # List of values from 1 to totalLength inclusive
    totalLength = length*width
    numList = [[(k+1) for k in range(totalLength)] for k in range(totalLength)]
    
    # Records the invalid numbers at coordinates
    invalidList = [[[] for a in range(totalLength)] for b in range(totalLength)]
    
    # Displaying the program as it runs (Only for the demo)
    display = ""
    
    # Iterate through the rows
    i = 0
    while(i < totalLength):
        
        # Shuffle the inputs for variance in output (Seeded for the demo)
        random.seed(seed[i])
        random.shuffle(numList[i])
        
        # Iterate through the columns
        j = 0
        while(j < totalLength):
            # Debugging/Stats
            iter = iter + 1 
            
            # Automatically assume there isn't a valid input
            valid = False
            
            # Iterate through number list
            for k in range(0, len(numList[i])):
                input = numList[i][k]
                
                # Check the rest of the column and box to see if its valid
                if (checkCol(board, length, width, i, j, input) & checkCell(board, length, width, i, j, input)) & (not(input in invalidList[i][j])):
                    valid = True
                    board[i][j] = numList[i].pop(k)
                    break
            
            # Backtrack if not valid
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
                
            # Display the board as it runs (Only for the demo)
            display = ""
            display += "- Iteration #" + str(iter) + " -\n"
            display += "numList: " + str(numList[i]) + "\n"
            display += "(x,y): (" + str(j) + "," + str(i) + ")\n"
            display += "Board: \n"
            display += boardToString(board, length, width) + "\n"
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
