'''
Created on Nov 9, 2016

@author: micro
'''
import numpy as np 
import cv2

def find_marker(image):
    #convert the image to grayscale, blur it, and detect edges (in that order))
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (5,5), 0)
    edged = cv2.Canny(gray, 35, 125)
    
    #find the contours in the edged image and keep the largest one;
    
    (_,cnts,_) = cv2.findContours(edged.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    _, c = max(cnts, key = cv2.contourArea)
    #compute the bounding box of the paper region and return it
    return cv2.minAreaRect(c)

def distance_to_camera(knownWidth, focalLength, perWidth):
    #compute and return the distance from the marker and the camera
    return (knownWidth*focalLength) / perWidth

KNOWN_DISTANCE = 24.0
KNOWN_WIDTH = 11.0

image = cv2.imread("C:/python27/image-2ft.jpg")
marker = find_marker(image)
focalLength = (marker[1][0]*KNOWN_DISTANCE) / KNOWN_WIDTH

cap = cv2.VideoCapture(0)

while True:
    _, frame = cap.read()
    marker = find_marker(frame)
    inches = distance_to_camera(KNOWN_WIDTH, focalLength, marker[1][0])
    
    #draw a bounding box around the image and display it
    box = np.int0(cv2.boxPoints(marker))
    cv2.drawContours(frame, [box], -1, (0,255,0), 2)
    cv2.putText(frame, "%.2fft" % (inches / 12),
                (frame.shape[1] - 200, frame.shape[0] - 20),
                cv2.FONT_HERSHEY_SIMPLEX, 2.0, (0,255,0), 3)
    cv2.imshow("Distance calculation", frame)
    cv2.waitKey(0)
    
    k = cv2.waitKey(5) & 0xFF
    if k == 7:
        break
    
cv2.destroyAllWindows()
cap.release()


