'''
Created on Nov 9, 2016

@author: micro
'''
import numpy as np
import cv2
import os

#####################################
#Detects one image inside of another#
#####################################

img_bgr = cv2.imread("C:/Python27/opencv-template-matching-python-tutorial.jpg")
print os.path.exists("C:/Python27/opencv-template-matching-python-tutorial.jpg")
img_gray = cv2.cvtColor(img_bgr, cv2.COLOR_BGR2GRAY)

template = cv2.imread("C:/Python27/opencv-template-for-matching.jpg")

print os.path.exists("C:/Python27/opencv-template-for-matching.jpg")


w,h = template.shape[:-1]
                     
res = cv2.matchTemplate(img_bgr, template, cv2.TM_CCOEFF_NORMED)
threshold = 0.725
locations = np.where(res >= threshold)

for point in zip(*locations[::-1]):
    cv2.rectangle(img_bgr, point, (point[0]+w, point[1]+h), (0,255,255), 2)
    
cv2.imshow('detected', img_bgr)
cv2.waitKey(0)
cv2.destroyAllWindows()