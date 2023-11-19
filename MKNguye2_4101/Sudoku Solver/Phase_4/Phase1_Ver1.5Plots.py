import matplotlib.pyplot as plt
import numpy as np
import statistics


plt.xlabel('Total Size (Length*Width of Subgrids)')
plt.ylabel('Time (Seconds) (Average)')
#plt.ylim(0, .0175)
plt.title('Creating Boards (Linear Time Method)')


x = []
y = []
avg = []
avgSize = []

length = 1
while length < 10:
    width = 1
    while width < 10:


        dirName = "./Ver1.5Boards/" + str(length) + "x" + str(width) + "Boards/TimeFile.txt"
        size = length*width*length*width 
        
        timeFile = open(dirName, "r")
        timeList = []
        fileElements = timeFile.readlines()

        for item in fileElements:
            timeList.append( float(item) )
            y.append( float(item) )
            x.append( size )

        avg.append( sum(timeList) / len(timeList) )
        avgSize.append(size)

        width += 1
    length += 1
        

#plt.scatter( np.array(x), np.array(y), color="blue", alpha=.01)
plt.scatter( np.array(avgSize), np.array(avg), color="red", alpha=1)

plt.show()