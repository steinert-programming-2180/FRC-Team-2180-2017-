'''
Created on Nov 8, 2016

@author: micro
'''
import cv2
import numpy as np


img1 = cv2.imread("C:/Python27/3D-Matplotlib.png")
img2 = cv2.imread("C:/Python27/mainsvmimage.png")

weighted = cv2.addWeighted(img1, 0.6, img2, 0.4, 0.0)

cv2.imshow("weighted",weighted)
cv2.waitKey()
cv2.destroyAllWindows()

#Getting this error: 
#Variable references non-existent resource : 
#${workspace_loc:OpenCV/Arithmetics_and_Logic.py}
#################################################
#ERROR NOW FIXED