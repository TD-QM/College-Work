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
def makeBoard(length, width, num):
    tic = time.perf_counter()
    iter = 0
    totalLength = length*width
    valid = False
    board = [[0 for j in range(length*width)] for j in range(length*width)]
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
            
            toc = time.perf_counter()
            if (toc - tic) > (1.25 ** (length*width)):
                return board, False, 0, 0
            
            c += 1
            if not valid:
                c -= 1
        if not valid:
            r -= 1
        r += 1

    toc = time.perf_counter()
    print("Board" + str(length) + "x" + str(width) + "#" + str(num))
    print("Total Iterations: " + str(iter))
    timeTaken = toc-tic
    print("Ran for " + str(timeTaken) + " seconds")
    return board, True, timeTaken, iter
                    
                    
    
        

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


length = 1
while length < 25:
    width = 1
    while width < 25:

        if length*width > 25:
            break

        dirName = "./Ver1.2Boards/" + str(length) + "x" + str(width) + "Boards/"
        try:
            os.mkdir(dirName)
        except OSError as error:
            pass

        timeFileName = dirName + "TimeFile.txt"
        timeFile = open(timeFileName, "w")
        timer = ""

        iterFileName = dirName + "Iterations.txt"
        iterFile = open(iterFileName, "w")
        iter = ""

        resetFileName = dirName + "ResetFile.txt"
        resetFile = open(resetFileName, "w")
        resets = 0

        failedBoardsName = dirName + "FailedBoards.txt"
        failedBoardsFile = open(failedBoardsName, "w")
        failedBoards = ""

        i = 0
        while i < 101:
            boardName = dirName + str(length) + "x" + str(width) + "Board" + str(i) + ".txt"
            timeFileName = dirName + "TimeFile.txt"

            boardFile = open(boardName, "w")

            tic = time.perf_counter()

            board, completed, timeTaken, iterations = makeBoard(length, width, i)

            if not completed:
                failedBoards += boardToTxtFormat(board, length, width) + "\n"
                resets += 1
            else:
                boardFile.write(boardToTxtFormat(board, length, width))
                timer += str(timeTaken) + "\n"
                iter += str(iterations) + "\n"
                i += 1
            
            boardFile.close()

        timeFile.write(timer)
        iterFile.write(iter)
        resetFile.write(str(resets))
        failedBoardsFile.write(failedBoards)

        timeFile.close()
        iterFile.close()
        resetFile.close()
        failedBoardsFile.close()
        
        width += 1
    length += 1

#makeBoard(board)
