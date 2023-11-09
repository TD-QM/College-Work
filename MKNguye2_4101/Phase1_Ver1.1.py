'''
Version 1.1

Still in the experimental phase and trying to get things to work
This one panics too early and deletes two entire rows worth of cells when it encounters an error, causing it to be insanely slow
Deleting an entire row and trying again is the equivalent to bogo sort, which is why it wasn't used (seen in Version 1.3)
'''

import sys, math, random, time, os

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
    
    print(output)

def makeBoard(board, length, width):
    iter = 0
    totalLength = length*width
    valid = False
    
    r = 0
    while(r < totalLength):
        
        numList = [(k+1) for k in range(totalLength)]
        random.shuffle(numList)
        #print("New numList: " + str(numList))
        
        c = 0
        while(c < totalLength):
            valid = False
            
            for k in range(0, len(numList)):
                if checkCol(board, length, width, c, numList[k]) & checkCell(board, length, width, r, c, numList[k]):
                    valid = True
                    board[r][c] = numList.pop(k)
                    break
            
            if not valid:
                board[r] = [0 for l in range(totalLength)]
                r -= 1
                #print("r: " + str(r))
                board[r] = [0 for l in range(totalLength)]
            
            c += 1
            if not valid:
                break
        if not valid:
            r -= 1
        r += 1

                    
    
def checkCol(board, length, width, c, input):
    for i in range(0, length*width):
        if input == board[i][c]:
            return False
    return True

def checkCell(board, length, width, r, c, input):
    leftWall = int(c/length) * length
    rightWall = leftWall + length
    topWall = int(r/width) * width
    botWall = topWall + width
    
    for i in range(topWall, botWall):
        for j in range(leftWall, rightWall):
            if input == board[r][c]:
                return False
    return True

    

length = int(sys.argv[1])
width = int(sys.argv[2])
area = length*width


board = [[0 for i in range(area)] for i in range(area)]
    
tic = time.perf_counter()
makeBoard(board, length, width)
printBoard(board, length, width)
toc = time.perf_counter()
print("Ran for " + str(tic - toc) + " seconds")

#makeBoard(board)
