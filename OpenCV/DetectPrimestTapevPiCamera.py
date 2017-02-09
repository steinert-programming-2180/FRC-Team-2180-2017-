import sys
sys.path.append('/usr/local/lib/python3.4/site-packages')

import cv2
import numpy as np
import socket
from picamera.array import PiRGBArray
from picamera import PiCamera
import time
from networktables import NetworkTables
import logging
logging.basicConfig(level=logging.DEBUG) 

NetworkTables.initialize(server = '10.21.80.2')
sd = NetworkTables.getTable('SmartDashboard')

camera = PiCamera()
camera.resolution = (640, 480)
camera.framerate = 32
rawCapture = PiRGBArray(camera, size = (640, 480))

time.sleep(0.1)

count = 1
for rawFrame in camera.capture_continuous(rawCapture, format="bgr", use_video_port = True):
    frame = rawFrame.array
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
            sd.putNumber('center x', center[0])
            sd.putNumber('center y', center[1])
            print(str(count) + ": (" + str(sd.getNumber('center x')) + ', ' + str(sd.getNumber('center y')) + ')')
            print(str(w) + 'x' + str(h))
            	
    #cv2.imshow('Frame', frame)
    count += 1
    
    rawCapture.truncate(0)
	
    if cv2.waitKey(1) & 0xFF == ord('q'):
	    break
	    
cv2.destroyAllWindows()
