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
    
    return output

def makeBoard(board, length, width):
    iter = 0
    totalLength = length*width
    valid = False
    numList = [(a+1) for a in range(totalLength)]
    
    r = 0
    while(r < totalLength):
        random.shuffle(numList)
        #print("New numList: " + str(numList))
        
        iter = iter + 1 
        valid = True
            
        for k in range(totalLength):
            input = numList[k]
            validCol = checkCol(board, r, k, input)
            validCell = checkCell(board, length, width, r, k, input)
            if (not validCol) | (not validCell):
                valid = False
                break
            
        if valid:
            board[r] = [val for a, val in enumerate(numList)]
            r += 1

        display = ""
        display += "- Iteration #" + str(iter) + " -\n"
        display += "numList: " + str(numList) + "\n"
        display += "row: " + str(r) + "\n"
        display += "validity: " + str(valid) + "\n"
        display += "Board: \n"
        display += printBoard(board, length, width) + "\n"
        time.sleep(0.025)
        os.system("cls")
        print(display)
                    
    
        

                    
    
def checkCol(board, y, x, input):
    for i in range(0, y):
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


    

length = int(sys.argv[1])
width = int(sys.argv[2])
area = length*width


board = [[0 for i in range(area)] for i in range(area)]

tic = time.perf_counter()
makeBoard(board, length, width)
#printBoard(board, length, width)
toc = time.perf_counter()
print("Ran for " + str((toc - tic)) + " seconds")


#makeBoard(board)
