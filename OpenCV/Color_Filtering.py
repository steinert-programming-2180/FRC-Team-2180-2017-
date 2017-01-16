'''
Created on Nov 8, 2016

@author: micro
'''
import cv2
import numpy as np

cap = cv2.VideoCapture(0)

while True:
    _, frame = cap.read() #The underscore means that the first returned value of read() isn't needed, so we can get it and have a placeholder for it
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    
    lower_red = np.array([150,150,50]) #lower limit of red colors
    upper_red = np.array([180,255,150]) #upper limit of red colors
    
    mask = cv2.inRange(hsv, lower_red, upper_red) #finds valid hsv colors in image in range [lower_red,upper_red], makes that white and everything else black
    res = cv2.bitwise_and(frame, frame, mask=mask) #takes white regions of mask and converts them back to the colors of the original image
    
    kernel = np.ones((15,15), np.float32)/225 #A kernel (convolution matrix) is the array of the values of the pixels that appear in the res; to blur the image, add all the values of the kernel and divide by the number of elements in the kernel
    smoothed = cv2.filter2D(res,-1,kernel)
    
    blur = cv2.GaussianBlur(res,(15,15),0)
    median = cv2.medianBlur(res,15) #least noisy (USE THIS ONE) -->
    #medianBlur takes res and applies a blur of a factor of 15 to it
    bilateral = cv2.bilateralFilter(res, 15, 75, 75) #Don't use this
    
    cv2.imshow('frame',frame)
    #cv2.imshow('mask',mask)
    cv2.imshow('res',res)
    #cv2.imshow('smoothed',smoothed)
    cv2.imshow('blur',blur)
    cv2.imshow('median',median)
    cv2.imshow('bilateral',bilateral)
    
    k = cv2.waitKey(5)
    if k == 27: #enter '27' into keyboard to close video stream
        break
    
cv2.destroyAllWindows()
cap.release()