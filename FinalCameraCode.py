#this works!!!
import sys
sys.path.append('/usr/local/lib/python3.4/site-packages')
import cv2
import numpy as np
import time
from networktables import NetworkTables
import logging
import socket
import sys

logging.basicConfig(level=logging.DEBUG) 

cap = cv2.VideoCapture(0)

time.sleep(0.1)

count = 1
while True:
    _, frame = cap.read()
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    blurred = cv2.GaussianBlur(gray, (11, 11), 0)
    thresh = cv2.threshold(blurred, 220, 255, cv2.THRESH_BINARY)[1]
	
    contours = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]
    center = None
    
    if len(contours) > 0:
        c = max(contours, key = cv2.contourArea)
        x,y,w,h = cv2.boundingRect(c)
        M = cv2.moments(c)
        if (M['m00'] == 0):
            M['m00'] = 1
        center = int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"])
		
        if w > 20:
            cv2.rectangle(frame, (x,y), (x+w,y+h), (0, 255, 255), 2)
            cv2.circle(frame, center, 5, (255, 0, 0), -1)
            height, width, channels = frame.shape
            cv2.circle(frame, (int(width/2), int(height/2)), 5, (0, 255, 0), -1)
            print(str(count) + ": (" + str(center[0]) + ', ' + str(center[1]) + ')')
            print(str(w) + 'x' + str(h))
            
            if center[0] < width / 2:
                print('Left')
            elif center[0] > width / 2:
                print('Right')
            else:
                print('Center')
            	
    cv2.imshow('Frame', frame)
    count += 1
	
    if cv2.waitKey(1) & 0xFF == ord('q'):
	    break

    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as msg:
        print('Failed to create socket. Error code: '
              + str(msg[0]) + ' , Error message: ' + msg[1])
        sys.exit()

    print('Socket Created')

    host = '10.21.80.104'
    port = 8000

    try:
        remote_ip = socket.gethostbyname(host)
    except socket.gaierror:
        print('Hostname could not be resolved. Exiting')
        sys.exit()

    print('IP address of ' + host + ' is ' + remote_ip)
   
    s.connect((remote_ip, port))

    print('Socket connected to ' + host + ' on IP ' + remote_ip)

    message = str(center)

    try:
        s.sendall(bytes(message.encode()))
    except socket.error:
        print('Send failed')
        sys.exit()
    
    print('Message sent successfully')

    reply = s.recv(4096)
    print(reply.decode())

s.close()
cap.release()
cv2.destroyAllWindows()
