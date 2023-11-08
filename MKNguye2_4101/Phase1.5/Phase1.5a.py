import sys, math, random, time, os

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

def makeBaseBoard():
    innerLength = int(sys.argv[1])
    innerWidth = int(sys.argv[2])
    outerLength = innerWidth
    outerWidth = innerLength
    area = innerLength * innerWidth

    baseBoard = [[0 for a in range(innerLength)] for b in range(innerWidth)]

    i = 1
    for r in range(innerWidth):
        for c in range(innerLength):
            baseBoard[r][c] = i
            i += 1


    actualBoard = [[0 for a in range(area)] for b in range(area)]
    print(boardToString(actualBoard, innerLength, innerWidth))

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

modifiedBoard = copy(actualBoard)


