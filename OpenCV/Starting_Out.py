'''
Created on Nov 7, 2016

@author: micro
'''
import cv2 #OpenCV library
import numpy as np #numerical Python library
import matplotlib.pyplot as plt #more math libraries
import os
image_path = "C:/python27/Robotics_Letter.jpg"
print os.path.exists(image_path)

cv2.namedWindow("image", cv2.WINDOW_NORMAL)
img = cv2.imread(image_path,cv2.IMREAD_GRAYSCALE) #reads image
cv2.imshow("image",img) #shows image in a new window
cv2.waitKey(0) #waits for time in ms for a user to press a key
cv2.destroyAllWindows() #closes all windows once user presses a key
#print (type(img))