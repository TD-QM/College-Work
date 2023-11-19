import matplotlib.pyplot as plt
import numpy as np


plt.xlabel('Total Size')
plt.ylabel('Time (Seconds)')
plt.ylim(0, .0175)
plt.title('Phase 1')


x = []
y = []
avg = []
avgSize = []

for length in range(1, 11):
    for width in range(1, 11):
        dirName = "Ver1.5Boards/" + str(length) + "x" + str(width) + "Boards/TimeFile.txt"
        size = length*width*length*width 
        timeFile = open(dirName, "r")
        timeList = []
        fileElements = timeFile.readlines()
        for item in fileElements:
            timeList.append( float(item) )

        y.append(timeList)
        x.append( [ size for a in range( len(timeList) )] )
        avg.append( sum(timeList) / len(timeList) )
        avgSize.append(size)
        



#plt.scatter( np.array(x), np.array(y), color="cyan", alpha=.01)
plt.scatter( np.array(avgSize), np.array(avg), color="red", alpha=1)

plt.show()