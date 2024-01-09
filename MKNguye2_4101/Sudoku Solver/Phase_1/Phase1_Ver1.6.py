'''
Version 1.6

Early attempts at using AC3 to generate a board
Because AC3 needs some basis to go off of, it'll be hard to actually apply it to the generation of a board. Maybe fill in half of it randomly and go from there?
'''

import time, random


# Generates a board with a specified length and width of the inner boards
def makeBoard(board, length, width):
    # Start timer
    tic = time.perf_counter()
    # For data
    iter = 0
    
    totalLength = length*width
    valid = False
    numList = [[(a+1) for a in range(totalLength)] for a in range(totalLength)]
    invalidList = [[[] for a in range(totalLength)] for b in range(totalLength)]
    
    r = 0
    while(r < totalLength):
        random.shuffle(numList[r])
        #print("New numList: " + str(numList))
        
        c = 0
        while(c < totalLength):
            iter = iter + 1 
            valid = False
            
            for k, input in enumerate(numList[r]):
                if (checkCol(board, length, width, r, c, input) & checkCell(board, length, width, r, c, input)) & (not(input in invalidList[r][c])):
                    valid = True
                    board[r][c] = numList[r].pop(k)
                    break
            
            if not valid:
                if c == 0:
                    board[r][c] = 0
                    for k in range(totalLength):
                        invalidList[r][c] = []
                    r -= 1
                    c = totalLength - 1
                    numList[r].append(board[r][c])
                    invalidList[r][c].append(board[r][c])
                    board[r][c] = 0
                    
                else:
                    invalidList[r][c] = []
                    c -= 1
                    numList[r].append(board[r][c])
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

def checkRow(board, length, width, y, x, input):
    for i in range(0, length*width):
        if input == board[y][i]:
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