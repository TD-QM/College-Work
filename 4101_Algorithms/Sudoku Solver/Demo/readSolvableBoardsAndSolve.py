#read SOLVABLE BOARDS, THEN SOLVE

import os
import random
import re
import random
import time
import copy
import timeit
import math


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


def isSafe(grid, row, col, num, subgrid_rows, subgrid_cols):
    # Check if 'num' is not in the current row
    for x in range(len(grid[0])):
        if grid[row][x] == num:
            return False

    # Check if 'num' is not in the current column
    for x in range(len(grid)):
        if grid[x][col] == num:
            return False

    # Check if 'num' is not in the current sub-grid
    startRow, startCol = subgrid_rows * (row // subgrid_rows), subgrid_cols * (col // subgrid_cols)
    for i in range(subgrid_rows):
        for j in range(subgrid_cols):
            if grid[startRow + i][startCol + j] == num:
                return False

    return True

def solveSudoku(grid, row, col, subgrid_rows, subgrid_cols):
    if row == len(grid) - 1 and col == len(grid[0]):
        return True

    if col == len(grid[0]):
        row += 1
        col = 0

    if grid[row][col] > 0:
        return solveSudoku(grid, row, col + 1, subgrid_rows, subgrid_cols)

    for num in range(1, max(len(grid), len(grid[0])) + 1, 1):
        if isSafe(grid, row, col, num, subgrid_rows, subgrid_cols):
            grid[row][col] = num
            if solveSudoku(grid, row, col + 1, subgrid_rows, subgrid_cols):
                return True
            grid[row][col] = 0

    return False





grid = importBoard("./Demo/DemoBoardRemoved.txt")
print(boardToString(grid, 3, 4))
solveSudoku(grid, 0, 0, 3, 4)
print(boardToString(grid, 3, 4))


#############################################################################################################
    
    

   