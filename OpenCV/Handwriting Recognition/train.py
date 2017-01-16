'''
Created on Dec 22, 2016

@author: micro
'''
import joblib
from sklearn.svm import LinearSVC
import argparse

import imutils
import numpy as np
import mahotas
import cv2

from skimage import feature

from __future__ import print_function 


##################################################################################################################################################################
def load_digits(datasetPath):
    data = np.genfromtxt(datasetPath, delimiter = ",", 
                         dtype = "uint8")
    target = data[:,0]
    data = data[:, 1:].reshape(data.shape[0], 28, 28)
    
    return (data, target)

def deskew(image, width):
    (h,w) = image.shape[:2]
    moments = cv2.moments(image)
    
    skew = moments["mu11"] / moments["mu02"]
    M = np.float32([[1, skew, -0.5 * w * skew], [0, 1, 0]])
    image = cv2.warpAffine(image, M, (w, h),
                           flags = cv2.WARP_INVERSE_MAP | cv2.INTER_LINEAR)
    image = imutils.resize(image, width = width)
    
    return image

def center_extent(image, size):
    (eW,eH) = size
    
    if image.shape[1] > image.shape[0]:
        image = imutils.resize(image, width = eW)
    else:
        image = imutils.resize(image, height = eH)
    
    extent = np.zeros((eH, eH), dtype = "uint8")
    offsetX = (eW - image.shape[1]) // 2
    offsetY = (eH - image.shape[0]) // 2
    extent[offsetY:offsetY + image.shape[0], offsetX:offsetX + 
           image.shape[1]] = image
    
    CM = mahotas.center_of_mass(extent)
    (cY, cX) = np.round(CM).astype("int32")
    (dX, dY) = ((size[0] // 2) - cX, (size[1] // 2) - cY)
    M = np.float32([[1, 0, dX], [0, 1, dY]])
    extent = cv2.warpAffine(extent, M, size)
    
    return extent
##################################################################################################################################################################
class HOG:
    def __init__(self, orientations = 9, pixelsPerCell = (8,8),
                 cellsPerBlock = (3,3), transform = False):
        self.orientations = orientations
        self.pixelsPerCell = pixelsPerCell
        self.cellsPerBlock = cellsPerBlock
        self.transform = transform
    
    def describe(self, image):
        hist = feature.hog(image,
                           orientations = self.orientations,
                           pixels_per_cell = self.pixelsPerCell,
                           cells_per_block = self.cellsPerBlock,
                           transform_sqrt = self.transform)
        
        return hist
#################################################################################################################################################################

ap = argparse.ArgumentParser()
ap.add_argument("-d", "--dataset", required = True,
                help = "path to the dataset file")
ap.add_argument("-m", "--model", required = True,
                help = "path to where the model will be stored")
args = vars(ap.parse_args())

(digits, target) = load_digits(args["dataset"])
data = []

hog = HOG(orientations = 18, pixelsPerCell = (10, 10),
          cellsPerBlock = (1, 1), transform = True)

for image in digits:
    image = deskew(image, 20)
    image = center_extent(image, (20, 20))
    
    hist = hog.describe(image)
    data.append(hist)
    
model = LinearSVC(random_state = 42)
model.fit(data, target)

joblib.dump(model, args["model"])
#################################################################################################################################################################
ap1 = argparse.ArgumentParser()
ap1.add_argument("-m", "--model", required = True,
                 help = "path to t=where the model will be stored")
args1 = vars(ap1.parse_args())

model = joblib.load(args1["model"])

hog1 = HOG(orientations = 18, pixelsPerCell = (10, 10), 
           cellsPerBlock = (1, 1), transform = True)

cap = cv2.VideoCapture(0)

while True:
    _, frame = cap.read()
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) 
    
    blurred = cv2.GaussianBlur(gray, (5, 5), 0)
    edged = cv2.Canny(blurred, 30, 150)
    (_, cnts, _) = cv2.findContours(edged.copy(), cv2.RETR_EXTERNAL, 
                                    cv2.CHAIN_APPROX_SIMPLE)
    
    cnts = sorted([(c, cv2.boundingRect(c)[0]) for c in cnts], key = 
                  lambda x: x[1]) 
    
    for (c, _) in cnts:
        (x, y, w, h) = cv2.boundingRect(c) 
        
        if w >= 7 and h >= 20: 
            roi = gray[y:y + h, x:x + w] 
            thresh = roi.copy() 
            T = mahotas.thresholding.otsu(roi) 
            thresh[thresh > T] = 255 
            thresh = cv2.bitwise_not(thresh) 
            
            thresh = deskew(thresh, 20) 
            thresh = dataset.center_extent(thresh, (20, 20))
            
            cv2.imshow("thresh", thresh)
            
            hist = hog.describe(thresh) 
            digit = model.predict([hist])[0]
            print("I think that number is: {}".format(digit)) 
            cv2.rectangle(image, (x, y), (x + w, y + h), 
                      (0, 255, 0), 1) 
            cv2.putText(image, str(digit), (x - 10, y - 10), 
                    cv2.FONT_HERSHEY_SIMPLEX, 1.2, (0, 255, 0), 2)
            cv2.imshow("image", image) 
            cv2.waitKey(0)       