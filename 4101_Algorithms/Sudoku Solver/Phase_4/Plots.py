import matplotlib.pyplot as plt
import numpy as np
import statistics


plt.xlabel('Total Size (Length*Width of Subgrids)')
plt.ylabel('Time (Seconds) (Average)')
#plt.ylim(.000002, .0000055)
plt.title('Generating Boards (Backtracking Method) (With Outliers)')


x = []
y = []
avg = []
avgSize = []

length = 1
count = 0
while length < 25:
    '''
    if length == 1:
        width = 2
    else:
        width = 1
    '''
    width = 1

    while width < 25:
        '''
        if length == 5 & width == 5:
            break
        '''
        
        if length > 3:
            if length*width > 20:
                break
        elif length*width > 25:
            break
        
        dirName = "./Phase_1/Ver1.2Boards/" + str(length) + "x" + str(width) + "Boards/TimeFile.txt"
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
        count += 1
        

        width += 1
    length += 1
        

#plt.scatter( np.array(x), np.array(y), color="blue", alpha=.01)
plt.scatter( np.array(avgSize), np.array(avg), color="red", alpha=1)

plt.show()
