package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick left;
	Joystick right;
	
	
	//drive
	CANTalon rightFrontDrive;
	CANTalon leftFrontDrive;
	CANTalon leftRearDrive;
	CANTalon rightRearDrive;
	
// 	CANTalon[] driveMotors = {rightFrontDrive, leftFrontDrive, leftRearDrive, rightRearDrive};
	
	//steer
	CANTalon rightFrontSteer;
	CANTalon leftFrontSteer;
	CANTalon leftRearSteer;
	CANTalon rightRearSteer;
	
// 	CANTalon[] steerMotors = {rightFrontSteer, leftFrontSteer, leftRearSteer, rightRearSteer};
	
	
	int autoLoopCounter;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	left = new Joystick(0);
    	right = new Joystick(1);
    	
    	//even drive
    	
    	rightFrontDrive = new CANTalon(10);
    	leftFrontDrive = new CANTalon(20);
    	leftRearDrive = new CANTalon(30);
    	rightRearDrive = new CANTalon(40);
    	
    	//odd steering
    	
    	rightFrontSteer = new CANTalon(11);
    	leftFrontSteer = new CANTalon(21);
    	leftRearSteer = new CANTalon(31);
    	rightRearSteer = new CANTalon(41);
    	
    
    	
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //myRobot.arcadeDrive(left);
//    	rightFrontDrive.get(left.getRawAxis(0));
        
//    	driveMotors.set(left.getRawAxis(2)*.2);
//    	leftFrontDrive = driveMotors;
//    	leftRearDrive = driveMotors;
    	
//     	for (int x = 0; x < driveMotors.length; i++) {
//     		x.set(driveMotors[i].getRawAxis(0)*0.2);
//     	}
    	
//     	for (int x = 0; x < steerMotors.length; i++) {
//     		x.set(steerMotors[i].getRawAxis(2)*0.2);
//     	}
    	
   	rightFrontSteer.set(left.getRawAxis(2)*.2);
   	leftFrontSteer.set(left.getRawAxis(2)*0.2);
   	leftRearSteer.set(left.getRawAxis(2)*0.2);
   	rightRearSteer.set(left.getRawAxis(2)*0.2);
    	
   	rightFrontDrive.set(right.getRawAxis(0)*.2);
   	leftFrontDrive.set(right.getRawAxis(0)*.2);
   	leftRearDrive.set(right.getRawAxis(0)*.2);
   	rightRearDrive.set(right.getRawAxis(0)*0.2);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
