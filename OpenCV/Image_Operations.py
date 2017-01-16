'''
Created on Nov 8, 2016

@author: micro
'''
import numpy as np
import cv2
img = cv2.imread("C:/python27/watch.jpeg", cv2.IMREAD_COLOR)
px = img[55,55] #a single pixel
img[55,55] = [255,255,255] #changes color to white of the given pixel
print(px) #outputs color value in BGR of the pixel

#ROI- Region of Image
img[100:150,100:150] = [255,255,255] #converts pixels in ROI to white color

watch_face = img[40:175, 75:200]
img[0:135, 0:125] = watch_face #Same size ROI of original watch being converted to watch face

cv2.imshow("image",img)
cv2.waitKey(0)
cv2.destroyAllWindows()