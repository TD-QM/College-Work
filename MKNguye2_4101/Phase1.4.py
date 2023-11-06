import sys, math, random, time, os

# Generates the string representation of a board
def boardToString(board, length, width):
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


# Generates a board with a specified length and width of the inner boards
def makeBoard(board, length, width):
    iter = 0
    totalLength = length*width
    valid = False
    numList = [(a+1) for a in range(totalLength)]
    validList = [[[(a+1) for a in range(totalLength)] for a in range(totalLength)] for a in range(totalLength)]
    
    r = 0
    while(r < totalLength):
        numList.shuffle()
        
        c = 0
        while(c < totalLength):
            iter += 1
            valid = False
            for k, input in enumerate(numList[r]):
                if(input in validList):
                    updateCol(validList, length, width, r, c, input)
                    updateCell(validList, length, width, r, c, input)
            
            
        
                    
    
        

# Takes in a board with coordinates and a potential input at those coords. 
# Returns true if it satisfies the column dependency, false otherwise
def updateCol(validBoard, length, width, y, x, input):
    topWall = int(y/width) * width
    botWall = topWall + width
    
    for i in range(0, topWall):
        validBoard[i][x].remove(input)
    for i in range(botWall + 1, length*width):
        validBoard[i][x].remove(input)
        

# Takes in a board with coordinates and a potential input at those coords. 
# Returns true if it satisfies the inner board dependency, false otherwise
def updateCell(validBoard, length, width, y, x, input):
    leftWall = int(x/length) * length
    rightWall = leftWall + length
    topWall = int(y/width) * width
    botWall = topWall + width
    
    for i in range(topWall, botWall):
        for j in range(leftWall, rightWall):
            validBoard[i][j].remove(input)



    

length = int(sys.argv[1])
width = int(sys.argv[2])
area = length*width


dirName = "./" + str(length) + "x" + str(width) + "Boards/"
try:
    os.mkdir(dirName)
except OSError as error:
    pass

for i in range(1, int(sys.argv[3])+1 ):
    file = open(dirName + str(length) + "x" + str(width) + "Board" + str(i) + ".txt", "w")
    board = [[0 for j in range(area)] for j in range(area)]
    tic = time.perf_counter()
    makeBoard(board, length, width)
    file.write( boardToString(board, length, width) )
    toc = time.perf_counter()
    print("Ran for " + str((toc - tic)) + " seconds")


#makeBoard(board)
