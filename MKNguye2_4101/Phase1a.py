import sys, math, random


def printBoard(board, length, width):
    maxDigits = int(math.log(length*width, 10)) + 1
    horizontalLine = "-"*((1+maxDigits) * length * width) + "-"*((width-1)*(maxDigits+1)*2) + "-"*(width-1)
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
    
    for i in range(0, totalLength):
        
        numList = [(k+1) for k in range(totalLength)]
        random.shuffle(numList)
        print("New numList: " + str(numList))
        
        for j in range(0, totalLength):
            valid = False
            
            for k in range(0, len(numList)):
                
                if checkCol(board, length, width, i, j, numList[k]) & checkCell(board, length, width, i, j, numList[k]):
                    valid = True
                    board[i][j] = numList.pop(k)
                    break
                
            if not valid:
                if j == 0:
                    i = i - 2
                    board[i] = [0 for l in range(totalLength)]
                else:
                    numList.append(board[i][j-1])
                    board[i][j-2] = 0
                    j = j - 2
                
            
            iter += 1
            print("- Iteration #" + str(iter) + " -")
            print("numList: " + str(numList))
            print("(x,y): (" + str(i) + "," + str(j) + ")")
            printBoard(board, length, width)
            print()
            
            if not valid:
                break
                    
                
        
def checkRow(board, length, width, x, y, input):
    return
    
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
    
def backtrack(board, length, width, numList, x, y):
    
    return
    

length = int(sys.argv[1])
width = int(sys.argv[2])
area = length*width


board = [[0 for i in range(area)] for i in range(area)]
    
print()
makeBoard(board, length, width)
printBoard(board, length, width)

#makeBoard(board)
