import sys
sys.path.append('/usr/local/lib/python3.4/site-packages')

import numpy as np
import argparse
import cv2
import sys
import time
from networktables import NetworkTables
import logging
logging.basicConfig(level=logging.DEBUG)

NetworkTables.initialize(server='172.22.11.2')

sd = NetworkTables.getTable("SmartDashboard")

count = 1

ap = argparse.ArgumentParser()
ap.add_argument("-i", "--image", help = "path to the image file")
ap.add_argument("-r", "--radius", type = int,
	help = "radius of Gaussian blur; must be odd")
args = vars(ap.parse_args())

cap = cv2.VideoCapture(0)

while True:
	_, frame = cap.read()
	
	orig = frame.copy()
	
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	
	gray = cv2.GaussianBlur(gray, (5,5), 0)
	(minVal, maxVal, minLoc, maxLoc) = cv2.minMaxLoc(gray)
	image = orig.copy()
	cv2.rectangle(image, (int(maxLoc[0]+50),int(maxLoc[1])-50), (int(maxLoc[0]-50),int(maxLoc[1])+50), (255, 0, 0), 2)
	
	cv2.line(image, (int(maxLoc[0]+50),int(maxLoc[1])-50), (int(maxLoc[0]-50),int(maxLoc[1])+50), (255,0,255), 2)
	cv2.line(image, (int(maxLoc[0]-50),int(maxLoc[1])-50), (int(maxLoc[0]+50),int(maxLoc[1])+50), (255,0,255), 2)

	cv2.circle(image, maxLoc, 5, (0, 255, 255), -1)
	
	print(str(count) + ": " + str(maxLoc))
	count += 1

	cv2.imshow("Detect Tape", image)

	sd.putNumber('maxLoc x', maxLoc[0])
	sd.putNumber('maxLoc y', maxLoc[1])
	
	if cv2.waitKey(1) & 0xFF == ord('q'):
		break
	
cap.release()
cv2.destroyAllWindows()
