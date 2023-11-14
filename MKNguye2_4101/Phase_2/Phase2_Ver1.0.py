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

def validListToString(board, length, width):
    maxDigits = (int(math.log(length*width, 10)) + 1)*2 + 1
    horizontalLine = "-"*((maxDigits+1) * length * width) + "-"*((width)*(maxDigits)) + "-"*(width+1)
    totalLength = length*width
    output = horizontalLine + "\n"
    
    for i in range(0, totalLength):
        output += "|"
        
        for j in range(0, totalLength):
            if len(board[i][j]) == 0:
                numDigits = 2
            else:
                numDigits = int(math.log(len(board[i][j]), 10)) + 1
            output += " "*(maxDigits - numDigits + 1) + str(board[i][j])
            
            if (j+1) % length == 0:
                output += " "*(maxDigits) + "|"
                
        if (i+1) % width == 0:
            output += "\n" + horizontalLine
            
        output += "\n"
    
    return output
    


def solveBoard(board, innerLength, innerWidth):
    outerLength = innerWidth
    outerWidth = innerLength
    totalLength = innerLength * innerWidth
    validList = populateValidList(board, innerLength, innerWidth)
    display = ""
    
    while not boardSolved(board):
        while singleValid(board, validList, innerLength, innerWidth):
            pass
        while onlyValid(board, validList, innerLength, innerWidth):
            pass
    pass


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
    updateCell(board, validList, innerLength, innerWidth, row, col, val)
        
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

# Updates the validList of all of the squares in the cell
def updateCell(board, validList, innerLength, innerWidth, row, col, input):
    leftWall = int(col/innerLength) * innerLength
    rightWall = leftWall + innerLength
    topWall = int(row/innerWidth) * innerWidth
    botWall = topWall + innerWidth
    
    for row in range(topWall, botWall):
        for col in range(leftWall, rightWall):
            if(input in validList[row][col]):
                validList[row][col].remove(input)


def boardSolved(board):
    for row in board:
        for col in row:
            if col == 0:
                return False
    return True


def singleValid(board, validList, innerLength, innerWidth):
    totalLength = innerLength*innerWidth
    for row in range(totalLength):
        for col in range(totalLength):
            singleValidList = (len(validList[row][col]) == 1)
            notFilled = ( (board[row][col] == 0))
            if(singleValidList & notFilled):
                updateValidList(board, validList, innerLength, innerWidth, row, col, validList[row][col])
                board[row][col] = validList[row][col][0]
                validList[row][col] = []
                return True
    return False

def onlyValid(board, validList, innerLength, innerWidth):
    totalLength = innerLength*innerWidth
    for row in range(totalLength):
        for col in range(totalLength):
            
            checkColResult, validNum = checkCol(validList, innerLength, innerWidth, row, col)
            if(checkColResult):
                updateValidList(board, validList, innerLength, innerWidth, row, col, validNum)
                board[row][col] = validNum
                validList[row][col] = []
                return True
            
            checkRowResult, validNum = checkRow(validList, innerLength, innerWidth, row, col)
            if(checkRowResult):
                updateValidList(board, validList, innerLength, innerWidth, row, col, validNum)
                board[row][col] = validNum
                validList[row][col] = []
                return True
            
            checkCellResult, validNum = checkCell(validList, innerLength, innerWidth, row, col)
            if(checkCellResult):
                updateValidList(board, validList, innerLength, innerWidth, row, col, validNum)
                board[row][col] = validNum
                validList[row][col] = []
                return True
            
    return False
            
# Takes in a validList with coordinates and checks the rest of the column's validList
# If there's a value only present in the coords, then return both the number and True
# Otherwise, return False.
def checkCol(validList, innerLength, innerWidth, row, col):
    totalLength = innerLength*innerWidth
    colValidList = []
    
    for row2 in range(0, totalLength):
        for itemIndex in range(len(validList[row2][col])):
            alreadyInCellList = validList[row2][col][itemIndex] in colValidList
            currentCell = (row2 == row)
            if not (alreadyInCellList | currentCell):
                colValidList.append(validList[row2][col][itemIndex])
    
    for item in validList[row][col]:
        if not (item in colValidList):
            return True, item
        
    return False, 0

# Takes in a board with coordinates and a potential input at those coords. 
# Returns true if it satisfies the row dependency, false otherwise
def checkRow(validList, innerLength, innerWidth, row, col):
    totalLength = innerLength *  innerWidth
    rowValidList = []
    
    for col2 in range(0, totalLength):
        for itemIndex in range(len(validList[row][col2])):
            alreadyInCellList = validList[row][col2][itemIndex] in rowValidList
            currentCell = (col2 == col)
            if not (alreadyInCellList | currentCell):
                rowValidList.append(validList[row][col2][itemIndex])
    
    for item in validList[row][col]:
        if not (item in rowValidList):
            return True, item
    return False, 0

# Takes in a board with coordinates and a potential input at those coords. 
# Returns true if it satisfies the inner board dependency, false otherwise
def checkCell(validList, innerLength, innerWidth, row, col):
    leftWall = int(col/innerLength) * innerLength
    rightWall = leftWall + innerLength
    topWall = int(row/innerWidth) * innerWidth
    botWall = topWall + innerWidth
    cellValidList = []
    
    for row2 in range(topWall, botWall):
        for col2 in range(leftWall, rightWall):
            for itemIndex in range(len(validList[row2][col2])):
                alreadyInCellList = validList[row2][col2][itemIndex] in cellValidList
                currentCell = (row2 == row) & (col2 == col)
                
                if not (alreadyInCellList | currentCell):
                    cellValidList.append(validList[row2][col2][itemIndex])
                
    for item in validList[row][col]:
        if not (item in cellValidList):
            return True, item
    return False, 0


'''
The importBoard function takes in a filePath and returns the board at the file that the path is pointing towards
'''
def importBoard(filePath):
    file = open(filePath, "r")
    board = []
    fileElements = file.readlines()
    
    boardIndex = 0
    for item in fileElements:
        board.append([])
        
        item = item[1:len(item)-1]
        itemList = item.split()
        
        board[boardIndex] = [int(n) for n in itemList]
        boardIndex += 1
        
    return board
            

first_sudoku = [
    [5, 0, 1, 0, 0, 0, 6, 0, 4],
    [0, 9, 0, 3, 0, 6, 0, 5, 0],
    [0, 0, 0, 0, 9, 0, 0, 0, 0],
    [4, 0, 0, 0, 0, 0, 0, 0, 9],
    [0, 0, 0, 1, 0, 9, 0, 0, 0],
    [7, 0, 0, 0, 0, 0, 0, 0, 6],
    [0, 0, 0, 0, 2, 0, 0, 0, 0],
    [0, 8, 0, 5, 0, 7, 0, 6, 0],
    [1, 0, 3, 0, 0, 0, 7, 0, 2]
]



'''
tic = time.perf_counter()
print(boardToString(first_sudoku, 3, 3))
solveBoard(first_sudoku, 3, 3)
print(boardToString(first_sudoku, 3, 3))
toc = time.perf_counter()
timeTaken = toc - tic
print("Ran for " + str(timeTaken) + " seconds")
'''

board = importBoard("./Boards/3x3Boards/3x3Board3.txt")
print(board)
print(boardToString(board, 3, 3))