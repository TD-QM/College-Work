import matplotlib.pyplot as plt
import numpy as np

def importTime(filePath):
    file = open(filePath, "r")
    timeList = []
    fileElements = file.readlines()

    for item in fileElements:
        timeList.append( float(item) )
        
    return len(timeList), np.array(timeList)



plt.xlabel('Total Size')
plt.ylabel('Time (Seconds)')
plt.ylim(0, .0175)
plt.title('Phase 1')


x = np.array([])
y = np.array([])
avg = np.array([])
avgSize = np.array([])

for length in range(1, 11):
    for width in range(1, 11):
        dirName = "Ver1.5Boards/" + str(length) + "x" + str(width) + "Boards/TimeFile.txt"
        
        size = length*width*length*width
        
        ySize, time = importTime(dirName)

        y = np.append(y, time)
        x = np.append(x, np.array( [ (size) for a in range(ySize)] ))
        avg = np.append(avg, np.average(time))
        avgSize = np.append(avgSize, size)
        

print(avg.size)
print(avgSize.size)
#plt.scatter(x, y, color="cyan", alpha=.01)
plt.scatter(avgSize, avg, color="red", alpha=1)

plt.show()