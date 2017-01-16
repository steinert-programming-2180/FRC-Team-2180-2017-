'''
Created on Nov 8, 2016

@author: micro
'''
import cv2
import numpy as np
#Let's draw some shapes!
#Let's write some words!
img = cv2.imread("C:/python27/watch.jpeg", cv2.IMREAD_COLOR)
cv2.line(img, (0,0), (150,150), (0,255,0), 15)
cv2.rectangle(img, (15,25), (200,150), (0,255,0), 2)
cv2.circle(img, (100,63), 55, (0,0,255), 2)
pts = np.array([[10,5],[20,30],[70,20],[50,10]], np.int32)
cv2.polylines(img, [pts], True, (0,255,255), 3) #True connects last point with first point

font = cv2.FONT_HERSHEY_SIMPLEX
cv2.putText(img, "Why, hello there!", (0,130), font, 1, (200,255,255), 2, cv2.LINE_AA) #image, text, starting point, font, size, color, thickness, #smooth text

cv2.imshow("image",img)
cv2.waitKey(0)
cv2.destroyAllWindows()