import matplotlib.pyplot as plt
import numpy as np

def importTime(filePath):
    file = open(filePath, "r")
    timeList = []
    fileElements = file.readlines()

    for item in fileElements:
        timeList.append( float(item) )
        
    return np.array(timeList)



fig, ax = plt.subplots()

for length in range(1, 11):
    for width in range(1, 11):
        dirName = "Ver1.5Boards/" + str(length) + "x" + str(width) + "Boards/TimeFile.txt"
        
        size = length*width*length*width
        
        y = importTime(dirName)
        x = np.array( [ (size) for a in range(y.size)] )
        
        ax.scatter(x, y, color="cyan", alpha=.01)
        
        average = np.average(y)
        
        ax.scatter(size, average, color="red", alpha=.5)

plt.show()