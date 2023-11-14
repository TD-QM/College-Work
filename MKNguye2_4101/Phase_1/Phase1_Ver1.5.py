'''
Version 1.5

Attempts to put the problem into polynomial time by swapping around the rows and columns of an already valid board
While it does work, issues arise in the realm of variance as there's a set pattern for each board generated
    This can be remedied by having multiple base boards to generate off of, but there still exists a relatively finite set of these patterns
    Regular sudoku also has a finite set of patterns, but compared to the finite sets of this method, it's infinite
    
Also the first instance of the boardToString method actually fucking working properly
'''

import sys, math, random, time, os

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


def boardToTxtFormat(board, length, width):
    output = ""
    maxDigits = int(math.log(length*width, 10)) + 1

    for row in board:
        for col in row:
            numDigits = int(math.log(col, 10)) + 1
            output += (" " * (maxDigits-numDigits + 1)) + str(col)
        output += "\n"
    
    return output


def boardCloning(board):
    boardCopy = board[:]
    return boardCopy


def makeBaseBoard(innerLength, innerWidth, outerLength, outerWidth):
    area = innerLength * innerWidth

    baseBoard = [[0 for a in range(innerLength)] for b in range(innerWidth)]

    i = 1
    for r in range(innerWidth):
        for c in range(innerLength):
            baseBoard[r][c] = i
            i += 1

    actualBoard = [[0 for a in range(area)] for b in range(area)]

    baseRow = 0
    baseCol = 0
    for outerRow in range(outerWidth):
        for outerCol in range(outerLength):
            for innerRow in range(innerWidth):
                for innerCol in range(innerLength):
                    baseCol = (innerCol+outerRow)%innerLength
                    baseRow = (innerRow+outerCol)%innerWidth
                    actualBoard[ (outerRow*innerWidth)+innerRow ][ (outerCol*innerLength)+innerCol ] = baseBoard[baseRow][baseCol] + 0

    return actualBoard

def randomizeBoard(board, innerLength, innerWidth, outerLength, outerWidth):
    randomizeInnerCol(board, innerLength, innerWidth, outerLength, outerWidth)
    randomizeInnerRow(board, innerLength, innerWidth, outerLength, outerWidth)

def randomizeInnerCol(board, innerLength, innerWidth, outerLength, outerWidth):
    for outerCol in range(outerLength):
        offset = outerCol*innerLength

        colList = [[0 for i in range(innerWidth*outerWidth)] for i in range(innerLength)]
        
        for innerCol in range(innerLength):
            for wholeCol in range(innerLength*innerWidth):
                colList[innerCol][wholeCol] = board[wholeCol][offset + innerCol]
            
        random.shuffle(colList)
            
        for innerCol in range(innerLength):
            for wholeCol in range(innerLength * innerWidth):
                board[wholeCol][offset + innerCol] = colList[innerCol][wholeCol]
    
def randomizeInnerRow(board, innerLength, innerWidth, outerLength, outerWidth):
    for outerRow in range(outerWidth):
        offset = outerRow*innerWidth
        rowList = []
        
        for innerRow in range(innerWidth):
            rowList.append(board[offset+innerRow])
            
        random.shuffle(rowList)
            
        for innerRow in range(innerWidth):
            board[offset + innerRow] = rowList[innerRow]




for innerLength in range(1, 11):
    outerWidth = innerLength
    for innerWidth in range(1, 11):
        outerLength = innerWidth

        dirName = "./Ver1.5Boards/" + str(innerLength) + "x" + str(innerWidth) + "Boards/"
        try:
            os.mkdir(dirName)
        except OSError as error:
            pass
        timeFileName = dirName + "TimeFile.txt"
        timeFile = open(timeFileName, "w")
        timeFile.write("")
        timeFile.close()

        baseBoard = makeBaseBoard(innerLength, innerWidth, outerLength, outerWidth)

        for boardNum in range(1, 101):
            boardName = dirName + str(innerLength) + "x" + str(innerWidth) + "Board" + str(boardNum) + ".txt"
            timeFileName = dirName + "TimeFile.txt"

            boardFile = open(boardName, "w")
            timeFile = open(timeFileName, "a")

            boardInput = boardCloning(baseBoard)

            tic = time.perf_counter()

            randomizeBoard(boardInput, innerLength, innerWidth, outerLength, outerWidth)

            boardFile.write( boardToTxtFormat(boardInput, innerLength, innerWidth) )

            toc = time.perf_counter()
            timeFile.write(str(toc - tic) + "\n")
            
            boardFile.close()
            timeFile.close()
            
    start = False
