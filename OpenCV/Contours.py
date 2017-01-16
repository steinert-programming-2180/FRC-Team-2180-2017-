'''
Created on Nov 10, 2016

@author: micro
'''
import cv2
import numpy as np
import imutils

cap = cv2.VideoCapture(0)

while True:
    goal = cv2.imread("C:/Python27/goal.jpg")
    (goalHeight, goalWidth) = goal.shape[:2]
    status = " No Target"
    
    _, frame = cap.read()
    frame = imutils.resize(frame, height=640)
    
    result = cv2.matchTemplate(frame, goal, cv2.TM_CCOEFF)
    (_, _, minLoc, maxLoc) = cv2.minMaxLoc(result)
    
    center = (0,0)
    topLeft = maxLoc
    botRight = (topLeft[0] + goalWidth, topLeft[1] + goalHeight)
    roi = frame[topLeft[1]:botRight[1], topLeft[0]:botRight[0]]
    center = (topLeft[0] + goalWidth/2, topLeft[1] + goalHeight/2)
    
    if center > (0,0):
        status = "Target detected @ " + str(center)
    
    cv2.putText(frame, status, (20,40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255), 1)
    
    cv2.line(frame, (center[0]-25, center[1]), (center[0]+25, center[1]), (0,255,0), 1)
    cv2.line(frame, (center[0], center[1]-25), (center[0], center[1]+25), (0,255,0), 1)
    cv2.circle(frame, center, 25, (255,0,0), 2)
    
    cv2.imshow("Frame", imutils.resize(frame, height = 640)) 
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
cap.release()
cv2.destroyAllWindows()
    

    

