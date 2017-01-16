'''
Created on Nov 11, 2016

@author: micro
'''
import numpy as np
import argparse
import cv2
import imutils
from collections import deque

ap = argparse.ArgumentParser()
ap.add_argument("-v", "--video",
    help="path to the (optional) video file")
ap.add_argument("-b", "--buffer", type=int, default=64,
    help="max buffer size")
args = vars(ap.parse_args())

cap = cv2.VideoCapture(0)

lower_green = (29, 86, 6)
upper_green = (64, 255, 255)
pts = deque(maxlen=args["buffer"])

while True:
    _, frame = cap.read()
    
    frame = imutils.resize(frame, width=600)
    
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    
    mask = cv2.inRange(hsv, lower_green, upper_green)
    mask = cv2.erode(mask, None, iterations = 2)
    mask = cv2.dilate(mask, None, iterations = 2)
    
    contours = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]
    center = None
    
    if len(contours) > 0:
        c = max(contours, key = cv2.contourArea)
        ((x,y),radius) = cv2.minEnclosingCircle(c)
        M = cv2.moments(c)
        if (M["m00"] == 0): 
            M["m00"] = 1
        center = int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"])
        
        if radius>10:
            cv2.circle(frame, (int(x), int(y)), int(radius), (136,255,100), 2)
            cv2.circle(frame, center, 5, (0,0,255), -1)
            pts.appendleft(center)
    
    for i in xrange(1, len(pts)):
        if pts[i-1] is None or pts[i] is None:
            continue
        
        thickness = int(np.sqrt(args["buffer"] / float(i+1)) * 2.5)
        cv2.line(frame, pts[i-1], pts[i], (0,0,255), thickness)
    
    cv2.imshow("Frame", frame)
    
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
    
cap.release()
cv2.destroyAllWindows()

            
        