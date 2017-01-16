'''
Created on Nov 11, 2016

@author: micro
'''
from collections import deque
import numpy as np
import argparse
import imutils
import cv2
 
ap = argparse.ArgumentParser()
ap.add_argument("-v", "--video",
    help="path to the (optional) video file")
ap.add_argument("-b", "--buffer", type=int, default=32,
    help="max buffer size")
args = vars(ap.parse_args())

cap = cv2.VideoCapture(0)

lower_green = (29, 86, 6)
upper_green = (64, 255, 255)

pts = deque(maxlen=args["buffer"])
counter = 0
(dX, dY) = (0, 0)
direction = ""

while True:
    _, frame = cap.read()
    
    frame = imutils.resize(frame, width=600)
    blurred = cv2.GaussianBlur(frame, (11, 11), 0)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    
    mask = cv2.inRange(hsv, lower_green, upper_green)
    mask = cv2.erode(mask, None, iterations=2)
    mask = cv2.dilate(mask, None, iterations=2)
    
    contours = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]
    center = None
    
    if len(contours) > 0:
        c = max(contours, key=cv2.contourArea)
        ((x,y),radius) = cv2.minEnclosingCircle(c)
        M = cv2.moments(c)
        if (M["m00"] == 0): 
            M["m00"] = 1
        center = int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"])
        
        if radius>10:
            cv2.circle(frame, (int(x), int(y)), int(radius), (255,255,0), 2)
            cv2.circle(frame, center, 5, (255,0,255), -1)
            pts.appendleft(center)
            
    for i in np.arange(1, len(pts)):
        if pts[i-1]is None or pts[i] is None:
            continue
        
        if counter >= 10 and i==1 and len(pts) == args["buffer"]:
            dX = pts[-10][0] - pts[i][0]
            dY = pts[-10][1] - pts[i][1]
            (dirX, dirY) = ("", "")
            
            if np.abs(dX) > 20:
                dirX = "East" if np.sign(dX) == 1 else "West"
                
            if np.abs(dY) > 20:
                dirY = "North" if np.sign(dY) == 1 else "South"
                
            if dirX != "" and dirY != "":
                direction = "{}-{}".format(dirY, dirX)
                
            else:
                direction = dirX if dirX != "" else dirY
                
        thickness = int(np.sqrt(args["buffer"] / float(i + 1)))
        cv2.line(frame, pts[i - 1], pts[i], (0, 255, 0), thickness)
        
    cv2.putText(frame, direction, (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 0.65, (0, 0, 255), 3)
    cv2.putText(frame, "dx: {}, dy: {}".format(dX, dY), (10, frame.shape[0] - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.35, (0, 0, 255), 1)
    
    cv2.namedWindow("Frame", cv2.WND_PROP_FULLSCREEN) 
    cv2.setWindowProperty("Frame", cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)
    cv2.imshow("Frame", frame)
    counter += 1
    
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
    
cap.release()
cv2.destroyAllWindows()