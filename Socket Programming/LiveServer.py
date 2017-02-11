'''
Created on Feb 10, 2017

@author: Steinert FRC2180
'''
import socket
import sys

HOST = ''
PORT = 5000

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket created')

try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print('Bind failed. Error code: ' + str(msg[0]) + 'Message ' + msg[1])
    sys.exit()
    
print('Socket bind complete')

s.listen(10)
print('Socket now listening')

while True:
    conn, addr = s.accept()
    print('Connected with ' + addr[0] + ':' + str(addr[1]))
    
    data = conn.recv(1024).decode()
    reply = "OK... " + data
    if not data:
        break
    
    conn.sendall(reply.encode())

conn.close()
s.close()
