'''
Created on Nov 10, 2016

@author: micro
'''
import cv2
import numpy as np
import imutils


image = cv2.imread("C:/Python27/shapes_and_colors.jpg")

gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
blurred = cv2.GaussianBlur(gray, (5,5), 0)
thresh = cv2.threshold(blurred, 60, 255, cv2.THRESH_BINARY)[1]

contours = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
contours = contours[0] if imutils.is_cv2() else contours[1] #return values of findContours changed for OpenCV 3.0


for contour in contours:
    #compute the center of the contour
    M = cv2.moments(contour)
    if (M["m00"] == 0): #prevents division by zero error
        M["m00"] = 1
    contourX = int(M["m10"] / M["m00"])
    contourY = int(M["m01"] / M["m00"])
        
    #draw the contour and center of the shape on the image
    cv2.drawContours(image, [contour], -1, (0,255,0), 2)
    cv2.circle(image, (contourX, contourY), 4, (255,255,255), -1)
    cv2.putText(image, "center", (contourX - 20, contourY - 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255), 2)
        
cv2.imshow('image',image)
cv2.waitKey(0)
    
    
    
