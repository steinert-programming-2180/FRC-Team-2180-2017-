'''
Created on Nov 11, 2016

@author: micro
'''
import wpilib


class Test_Run (wpilib.IterativeRobot):
    
    def robotInit(self):
        self.motor = wpilib.CANTalon(1)
        self.motor = wpilib.CANTalon(2)
        self.motor = wpilib.CANTalon(3)
        self.motor = wpilib.CANTalon(4)
        
        self.motor.set(1)
        
        self.robot_drive = wpilib.RobotDrive(0,1)