'''
Version 1.2

Utilizes a backtracking algorithm that doesn't panic too much compared to 1.1

Input a length and width of the inner rectangle in the board for the first two inputs. The 3rd input is the number of boards of that size

Absurdly reliant on random chance and can spiral out of control, taking hours to complete a simple board that usually takes less than a second
    Sort of remedied by a kill switch based on time, but the problem still exists and will only continue doing so the larger the input size
'''

import sys, math, random, time, os

# Generates the string representation of a board
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
            numDigits = int(math.log(col, 10)) + 1
            output += (" " * (maxDigits-numDigits + 1)) + str(col)
        output += "\n"
    
    return output



# Generates a board with a specified length and width of the inner boards
def makeBoard(board, length, width):
    # Start timer
    tic = time.perf_counter()
    # For data
    iter = 0
    
    totalLength = length*width
    valid = False
    validList = populateValidList([[[] for a in range(totalLength)] for b in range(totalLength)], length, width)
    invalidList = [[[] for a in range(totalLength)] for b in range(totalLength)]
    
    r = 0
    while(r < totalLength):
        #print("New numList: " + str(numList))
        
        c = 0
        while(c < totalLength):
            iter = iter + 1 
            valid = False
            
            for k, input in enumerate(validList[r]):
                if (checkCol(board, length, width, r, c, input) & checkCell(board, length, width, r, c, input)) & (not(input in invalidList[r][c])):
                    valid = True
                    board[r][c] = validList[r].pop(k)
                    break
            
            if not valid:
                if c == 0:
                    board[r][c] = 0
                    for k in range(totalLength):
                        invalidList[r][c] = []
                    r -= 1
                    c = totalLength - 1
                    validList[r].append(board[r][c])
                    invalidList[r][c].append(board[r][c])
                    board[r][c] = 0
                    
                else:
                    invalidList[r][c] = []
                    c -= 1
                    validList[r].append(board[r][c])
                    invalidList[r][c].append(board[r][c])
                    board[r][c] = 0
            
            c += 1
            if not valid:
                c -= 1
        if not valid:
            r -= 1
        r += 1
    toc = time.perf_counter()
    print("Total Iterations: " + str(iter))
    print("Ran for " + str((toc - tic)) + " seconds")
                    
                    
    
        

# Takes in a board with coordinates and a potential input at those coords. 
# Returns true if it satisfies the column dependency, false otherwise
def checkCol(board, length, width, y, x, input):
    for i in range(0, length*width):
        if input == board[i][x]:
            return False
    return True

# Takes in a board with coordinates and a potential input at those coords. 
# Returns true if it satisfies the inner board dependency, false otherwise
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


def populateValidList(board, innerLength, innerWidth):
    totalLength = innerLength * innerWidth
    validList = [[[(a+1) for a in range(totalLength)] for a in range(totalLength) ] for a in range(totalLength)]
    
    for row in range(totalLength):
        for col in range(totalLength):
            if not (board[row][col] == 0):
                val = board[row][col]
                updateValidList(board, validList, innerLength, innerWidth, row, col, val)
                validList[row][col] = []
    return validList
        
def updateValidList(board, validList, innerLength, innerWidth, row, col, val):
    updateCol(board, validList, innerLength, innerWidth, row, col, val)
    updateRow(board, validList, innerLength, innerWidth, row, col, val)
    updateBox(board, validList, innerLength, innerWidth, row, col, val)
    
# Updates the validList of all of the squares in the column
def updateCol(board, validList, innerLength, innerWidth, row, col, input):
    topWall = int(row/innerWidth) * innerWidth
    botWall = topWall + innerWidth
    for row in range(0, topWall):
        if(input in validList[row][col]):
            validList[row][col].remove(input)
    for row in range(botWall, innerLength*innerWidth):
        if(input in validList[row][col]):
            validList[row][col].remove(input)
# Updates the validList of all of the squares in the row
def updateRow(board, validList, innerLength, innerWidth, row, col, input):
    leftWall = int(col/innerLength) * innerLength
    rightWall = leftWall + innerLength
    for col in range(0, leftWall):
        if(input in validList[row][col]):
            validList[row][col].remove(input)
    for col in range(rightWall, innerLength*innerWidth):
        if(input in validList[row][col]):
            validList[row][col].remove(input)
# Updates the validList of all of the squares in the box
def updateBox(board, validList, innerLength, innerWidth, row, col, input):
    leftWall = int(col/innerLength) * innerLength
    rightWall = leftWall + innerLength
    topWall = int(row/innerWidth) * innerWidth
    botWall = topWall + innerWidth
    for row in range(topWall, botWall):
        for col in range(leftWall, rightWall):
            if(input in validList[row][col]):
                validList[row][col].remove(input)


    

length = int(sys.argv[1])
width = int(sys.argv[2])
numBoards = int(sys.argv[3]) + 1
area = length*width


dirName = "./Boards/" + str(length) + "x" + str(width) + "Boards/"
try:
    os.mkdir(dirName)
except OSError as error:
    pass

timeFile = open(dirName + str(length) + "x" + str(width) + "TimeFile.txt", "a")



for i in range(1, numBoards):
    boardFile = open(dirName + str(length) + "x" + str(width) + "Board" + str(i) + ".txt", "w")
    board = [[0 for j in range(area)] for j in range(area)]

    tic = time.perf_counter()
    makeBoard(board, length, width)
    boardFile.write( boardToTxtFormat(board, length, width) )
    toc = time.perf_counter()

    timeTaken = toc - tic
    timeFile.write(str(timeTaken) + "\n")
    print("Ran for " + str((toc - tic)) + " seconds")


#makeBoard(board)
