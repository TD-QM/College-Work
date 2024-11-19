from Crypto.Cipher import DES
from Crypto.Util.Padding import pad, unpad

# Example
inputFile = open("example.txt", "r")
exampleText = bytes.fromhex(inputFile.readline())
exampleIV = bytes.fromhex(inputFile.readline())

key = bytes.fromhex("f007ba11ba5eba11")
cipher = DES.new(key, DES.MODE_ECB)

print("Example: ")
print(unpad(cipher.decrypt(exampleText), DES.block_size))
print()



# Cipher 1
inputFile = open("cipher1.txt", "r")
cipherText = bytes.fromhex(inputFile.readline())
iv = bytes.fromhex(inputFile.readline())

key = bytes.fromhex("f007ba11ba5eba11")
cipher = DES.new(key, DES.MODE_ECB)

print("Cipher 1 (ECB): ")
print(unpad(cipher.decrypt(cipherText), DES.block_size, style='pkcs7'))
print()



# Cipher
inputFile = open("cipher2.txt", "r")
cipherText = bytes.fromhex(inputFile.readline())
iv = inputFile.readline()
nonce = bytes.fromhex( iv[:len(iv)//2] )
iv = bytes.fromhex(iv)

key = bytes.fromhex("f007ba11ba5eba11")
cipher = DES.new(key, DES.MODE_CTR, nonce=nonce)

print("Cipher 2 (CTR): ")
print(cipher.decrypt(cipherText))
print()



# Cipher 3
inputFile = open("cipher3.txt", "r")
cipherText = bytes.fromhex(inputFile.readline())
iv = bytes.fromhex(inputFile.readline())

key = bytes.fromhex("f007ba11ba5eba11")
cipher = DES.new(key, DES.MODE_CBC, iv)

print("Cipher 3 (CBC): ")
print(cipher.decrypt(cipherText))
print()



# Cipher 4
inputFile = open("cipher4.txt", "r")
cipherText = bytes.fromhex(inputFile.readline())
iv = bytes.fromhex(inputFile.readline())

key = bytes.fromhex("f007ba11ba5eba11")
cipher = DES.new(key, DES.MODE_OFB, iv)

print("Cipher 4 (OFB): ")
print(cipher.decrypt(cipherText))
print()


# Cipher 5
inputFile = open("cipher5.txt", "r")
cipherText = bytes.fromhex(inputFile.readline())
iv = bytes.fromhex(inputFile.readline())

key = bytes.fromhex("f007ba11ba5eba11")
cipher = DES.new(key, DES.MODE_CFB, iv=iv, segment_size=64)

print("Cipher 5 (CFB): ")
print(cipher.decrypt(cipherText))
print()