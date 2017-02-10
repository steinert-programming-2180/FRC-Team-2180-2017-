'''
Created on Feb 10, 2017

@author: micro
'''
import socket
import sys

HOST = ''
PORT = 8888

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket Created')

try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print('Bind failed. Error code: ' + str(msg[0]) + 'Message ' + msg[1])
    sys.exit()
    
print('Socket bind complete')

s.listen(10)
print('Socket now listening')

conn, addr = s.accept()

print('Connected to ' + addr[0] + ':' + str(addr[1]))