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

def validListToString(validList, length, width):
    maxDigits = (int(math.log(length*width, 10)) + 1)*2 + 1
    horizontalLine = "-"*((maxDigits+1) * length * width) + "-"*((width)*(maxDigits)) + "-"*(width+1)
    totalLength = length*width
    output = horizontalLine + "\n"
    
    for i in range(0, totalLength):
        output += "|"
        
        for j in range(0, totalLength):
            if len(validList[i][j]) == 0:
                numDigits = 2
            else:
                numDigits = int(math.log(len(validList[i][j]), 10)) + 1
            output += " "*(maxDigits - numDigits + 1) + str(validList[i][j])
            
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
    status = 0
    
    while not boardSolved(board):
        while True:
            if not nakedSingle(board, validList, innerLength, innerWidth):
                status = 1
                break
            
        if (status == 1):
            status = 0
            while True:
                if hiddenSingle(board, validList, innerLength, innerWidth):
                    status = 2
                    break
                
        if (status == 2):
            status = 0
            while True:
                if lockedCandidates(board, validList, innerLength, innerWidth):
                    status = 3
                    break
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



def boardSolved(board):
    for row in board:
        for col in row:
            if col == 0:
                return False
    return True


'''
Checks the board for any Naked Single patterns and puts in the only valid number for that cell
Returns True if there's a change in the board, and False otherwise
https://sudoku9x9.com/naked_single.php
'''
def nakedSingle(board, validList, innerLength, innerWidth):
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


'''
Checks the board for any Hidden Single patterns and puts in the only valid solution for that cell
Returns True if there's a change in the board, and False otherwise
https://sudoku9x9.com/hidden_single.php
'''
def hiddenSingle(board, validList, innerLength, innerWidth):
    totalLength = innerLength*innerWidth
    for row in range(totalLength):
        for col in range(totalLength):
            
            checkRowResult, validNum = hiddenSingleRow(validList, innerLength, innerWidth, row, col)
            if(checkRowResult):
                updateValidList(board, validList, innerLength, innerWidth, row, col, validNum)
                board[row][col] = validNum
                validList[row][col] = []
                return True
            
            checkColResult, validNum = hiddenSingleCol(validList, innerLength, innerWidth, row, col)
            if(checkColResult):
                updateValidList(board, validList, innerLength, innerWidth, row, col, validNum)
                board[row][col] = validNum
                validList[row][col] = []
                return True
            
            checkBoxResult, validNum = hiddenSingleBox(validList, innerLength, innerWidth, row, col)
            if(checkBoxResult):
                updateValidList(board, validList, innerLength, innerWidth, row, col, validNum)
                board[row][col] = validNum
                validList[row][col] = []
                return True
    return False
# Checks the row for any hidden singles
def hiddenSingleRow(validList, innerLength, innerWidth, row, col):
    totalLength = innerLength *  innerWidth
    rowValidList = []
    
    for col2 in range(0, totalLength):
        for itemIndex in range(len(validList[row][col2])):
            alreadyInBoxList = validList[row][col2][itemIndex] in rowValidList
            currentBox = (col2 == col)
            if not (alreadyInBoxList | currentBox):
                rowValidList.append(validList[row][col2][itemIndex])
    
    for item in validList[row][col]:
        if not (item in rowValidList):
            return True, item
    return False, 0 
# Checks the column for any hidden singles
def hiddenSingleCol(validList, innerLength, innerWidth, row, col):
    totalLength = innerLength*innerWidth
    colValidList = []
    
    for row2 in range(0, totalLength):
        for itemIndex in range(len(validList[row2][col])):
            alreadyInBoxList = validList[row2][col][itemIndex] in colValidList
            currentBox = (row2 == row)
            if not (alreadyInBoxList | currentBox):
                colValidList.append(validList[row2][col][itemIndex])
    
    for item in validList[row][col]:
        if not (item in colValidList):
            return True, item
        
    return False, 0
def hiddenSingleBox(validList, innerLength, innerWidth, row, col):
    leftWall = int(col/innerLength) * innerLength
    rightWall = leftWall + innerLength
    topWall = int(row/innerWidth) * innerWidth
    botWall = topWall + innerWidth
    boxValidList = []
    
    for row2 in range(topWall, botWall):
        for col2 in range(leftWall, rightWall):
            for itemIndex in range(len(validList[row2][col2])):
                alreadyInBoxList = validList[row2][col2][itemIndex] in boxValidList
                currentBox = (row2 == row) & (col2 == col)
                
                if not (alreadyInBoxList | currentBox):
                    boxValidList.append(validList[row2][col2][itemIndex])
                
    for item in validList[row][col]:
        if not (item in boxValidList):
            return True, item
    return False, 0


'''
Checks the board for any Locked Candidate patterns present in any of the boxes, rows, and columns
Returns True if there's a change in the Valid List, and False otherwise
https://sudoku9x9.com/locked_candidates.php
'''
def lockedCandidates(board, validList, innerLength, innerWidth):
    totalLength = innerLength*innerWidth
    for boxes in range(totalLength):
        if lockedCandidatesBox(board, validList, innerLength, innerWidth, boxes):
            return True
    for rows in range(totalLength):
        if lockedCandidatesRow(board, validList, innerLength, innerWidth, rows):
            return True
    
#Checks the row for any Locked Candidate pattern
def lockedCandidatesRow(board, validList, innerLength, innerWidth, row):
    totalLength = innerLength*innerWidth
    
    
    pass
#Checks the column for any Locked Candidate pattern
def lockedCandidatesCol(board, validList, innerLength, innerWidth, col):
    pass
#Checks the box for any Locked Candidate pattern
def lockedCandidatesBox(board, validList, innerLength, innerWidth, box):
    totalLength = innerLength*innerWidth
    row = int(box/innerWidth)
    col = int(box%innerWidth)
    leftWall = col * innerLength
    rightWall = leftWall + innerLength
    topWall = row * innerWidth
    botWall = topWall + innerWidth
    for i in range (totalLength):
        num = i+1
        numList = []
        for row2 in range(topWall, botWall):
            for col2 in range(leftWall, rightWall):
                if (num in validList[row2][col2]):
                    numList.append([row2, col2])
                    
        if len(numList) > 1:
            sameRow = True
            for val in range(len(numList)-1):
                if not numList[val][0] == numList[val+1][0]:
                    sameRow = False
                    break
            if sameRow:
                updateRow(board, validList, innerLength, innerWidth, numList[0][0], leftWall, num)
                return True
                            
            sameCol = True
            for val in range(len(numList)-1):
                if not numList[val][1] == numList[val+1][1]:
                    sameCol = False
                    break
            if sameCol:
                updateCol(board, validList, innerLength, innerWidth, topWall, numList[0][1], num)
                return True
    return False
                
                
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
            




testBoard = [
    [0, 0, 0,  0, 0, 0,  0, 0, 0],
    [0, 0, 0,  0, 0, 0,  0, 0, 0],
    [0, 0, 0,  0, 0, 0,  0, 0, 0],
    
    [1, 2, 0,  0, 0, 0,  0, 0, 0],
    [0, 0, 0,  9, 0, 0,  0, 0, 0],
    [4, 5, 0,  0, 0, 0,  0, 0, 0],
    
    [0, 0, 0,  0, 0, 0,  0, 0, 0],
    [0, 0, 0,  0, 0, 0,  0, 0, 0],
    [0, 0, 0,  0, 0, 0,  0, 0, 0],
]

validList = populateValidList(testBoard, 3, 3)
lockedCandidates(testBoard, validList, 3, 3)
print(validListToString(validList, 3, 3))


'''
first_sudoku = [
    [8, 5, 3,  0, 0, 0,  0, 0, 0],
    [2, 0, 0,  7, 5, 4,  0, 0, 3],
    [0, 0, 9,  0, 0, 3,  0, 0, 6],
    
    [0, 0, 0,  9, 8, 6,  0, 0, 7],
    [0, 0, 6,  0, 0, 5,  0, 1, 0],
    [4, 3, 0,  2, 0, 0,  0, 0, 0],
    
    [0, 0, 0,  1, 0, 0,  4, 9, 5],
    [0, 0, 5,  0, 7, 0,  2, 0, 0],
    [1, 2, 0,  0, 0, 0,  0, 0, 8]
]
tic = time.perf_counter()
print(boardToString(first_sudoku, 3, 3))

solveBoard(first_sudoku, 3, 3)

print(boardToString(first_sudoku, 3, 3))
toc = time.perf_counter()
timeTaken = toc - tic
print("Ran for " + str(timeTaken) + " seconds")
'''