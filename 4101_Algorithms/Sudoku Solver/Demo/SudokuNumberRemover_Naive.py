import random
import copy
import os, time, math

def printing(arr):
    for i in range(len(arr)):
        for j in range(len(arr[i])):
            print(arr[i][j], end=" ")
        print()

#checks if number doesnt exist already within row/col
def isSafe(grid, row, col, num):
    # Check if 'num' is not in the current row
    for x in range(len(grid[0])):
        if grid[row][x] == num:
            return False

    # Check if 'num' is not in the current column
    for x in range(len(grid)):
        if grid[x][col] == num:
            return False
        
    return True

def isSafe(grid, row, col, num):
    # Check if 'num' is not in the current row
    for x in range(len(grid[0])):
        if grid[row][x] == num:
            return False

    # Check if 'num' is not in the current column
    for x in range(len(grid)):
        if grid[x][col] == num:
            return False
        
    return True

def solveSudoku(grid, row, col, numSolution):
    
    #if currently on last cell, board is a valid solution
    if row == len(grid) - 1 and col == len(grid[0]):
        numSolution += 1
        return numSolution

    #if at end of row, enter next row
    if col == len(grid[0]):
        row += 1
        col = 0

    #if number already exists in current cell, go to the next cell
    if grid[row][col] > 0:
        return solveSudoku(grid, row, col + 1, numSolution)

    #iterates through possible numbers to the cell
    for num in range(1, max(len(grid), len(grid[0])) + 1, 1):
        #if number can be placed, set cell to current number 
        if isSafe(grid, row, col, num):
            grid[row][col] = num

            #continue to next cell 
            numSolution = solveSudoku(grid, row, col + 1, numSolution)

            #if a board has more than 1 solution, return
            if numSolution > 1:
                return numSolution

            #if placed num is an invalid solution, remove num and go to next possible number
            grid[row][col] = 0

    return numSolution



# The following functions are made by Kristopher Nguyen
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






seed = 2
seedList = [(a+1) for a in range(9999)]
random.seed(seed)
random.shuffle(seedList)



grid = importBoard("./Demo/DemoBoardSolution.txt")
count = 0
print("Initial Grid:")
print(boardToString(grid, 3, 4))


while(count < 2):
    rRow = random.randint(0, len(grid[0])-1)
    rCol = random.randint(0, len(grid)-1)
    if grid[rRow][rCol] != 0:
        oldGrid = copy.deepcopy(grid)
        grid[rRow][rCol] = 0
        
        time.sleep(0.03)
        os.system("cls")
        print("Removing Numbers:")
        print(boardToString(grid, 3, 4))
        count = solveSudoku(grid, 0, 0, 0)
        if (count==2):
            grid = oldGrid

time.sleep(0.03)
os.system("cls")
print("Uniquely Solveable Grid:")
print(boardToString(grid, 3, 4))

file = open("./Demo/DemoBoardRemoved.txt", "w")
file.write(boardToTxtFormat(grid, 3, 4))
file.close()
