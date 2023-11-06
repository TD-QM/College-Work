import random, time, os


def cls(n = 0):
    if n == 0:
        os.system('cls')
    else:
        print('\b'*n)
        raise TypeError("Does this even fucking run")
        
def display(chars):
    s = '\n'.join(''.join(row) for row in chars)
    print(s)
    return len(s)


chars = []
for i in range(20):
    chars.append(["-"]*20)


for i in range(50):
    n = 0
    r = random.randint(0,19)
    c = random.randint(0,19)
    chars[r][c] = "X"
    os.system("cls")
    n = display(chars)