import sys, math

def format4DBoard(board):
    length = len(board)
    width = len(board[0])
    
    #horizontalLine = "--" + ("-"+("-"*(int(math.log(length*width, 10))+1)))*width*length + "----"*(width-1) + "---\n"
    #               beginning
    #                           dashes for the numbers (includes spacing before)
    #                                                                                          dashes for the middle sections                      Ending dashes
    horizontalLine = "--" + ("-"+("-"*(int(math.log(length*width, 10))+1)))*width*length + ("--"*(int(math.log(length*width, 10))+2))*(width-1) + "--\n"
    output = ""
    
    for i in range(0, length):              # Outer Row
        output += horizontalLine
        for j in range(0, width):           # Inner Row
            for k in range(0, width):      # Outer Col
                output += "| "
                for l in range(0, length):   # Inner Col
                    if board[i][k][j][l] == 0:
                        output += " "*(int(math.log(length*width, 10)) + 1) + str(board[i][k][j][l])
                    else:
                        output += " "*(int(math.log(length*width, 10))+1 - ((int(math.log(board[i][k][j][l], 10))))) + str(board[i][k][j][l])
                output += " " + " "*(int(math.log(length*width, 10))+1)
            output += "|\n"
    
    output += horizontalLine
    
    return output


length = int(sys.argv[1])
width = int(sys.argv[2])
area = length*width

board = [[[[0 for i in range(length)]for i in range(width)] for i in range(width)] for i in range(length)]

print(format4DBoard(board))