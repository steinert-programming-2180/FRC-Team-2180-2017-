package org.usfirst.frc.team2180.robot;

import org.usfirst.frc.team2180.commands.EndGame;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
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
	Joystick left2;
	Joystick right2;
	
	Button open1,
	 	   close1,
		   open2,
		   close2,
		   open3,
		   close3,
		   open4,
		   close4;
	
	
	CANTalon leftMotor;
	CANTalon rightMotor;
	
	CANTalon shooterMotor;
	
	CANTalon winch;
	
	int brake;
	
	DoubleSolenoid solenoid1;
	DoubleSolenoid solenoid2;
	DoubleSolenoid solenoid3;
	DoubleSolenoid solenoid4;
	
	Compressor robotCompressor;
	
	int autoLoopCounter;
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(leftMotor,rightMotor);
    	left = new Joystick(0);
    	right = new Joystick(1);
    	left2 = new Joystick(2);
    	right2 = new Joystick(3);
    	
    	open1 = new JoystickButton (left, 2);
    	close1 = new JoystickButton (left,3);
    	open2 = new JoystickButton (left, 4);
    	close2 = new JoystickButton (left,5);
    	open3 = new JoystickButton (right,2);
    	close3 = new JoystickButton (right,3);
    	open4 = new JoystickButton (right,4);
    	close4 = new JoystickButton (right,5);
    	
    	leftMotor = new CANTalon(10);
    	rightMotor = new CANTalon(20);
    	
    	shooterMotor = new CANTalon(30);
    	
    	winch = new CANTalon(40);
    	
    	solenoid1 = new DoubleSolenoid (0,0,1);
    	solenoid2 = new DoubleSolenoid (0,2,3);
    	solenoid3 = new DoubleSolenoid (0,4,5);
    	solenoid4 = new DoubleSolenoid (0,6,7);
    	
    	robotCompressor = new Compressor(0);
    	
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
    							  //50 loops (approximately 1 second) Alex
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
    		//myRobot.tankDrive(leftMotor, rightMotor);
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	brake = 0;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //myRobot.arcadeDrive(right2);
    	myRobot.tankDrive(left, right);
    	
    	/*if (open1){
    		solenoid1.set(DoubleSolenoid.Value.kForward);
    		
    	}*/
    	
    	shooterMotor.set(left.getRawAxis(0));
    	/*
    	 brakeLeft = new JoystickButton(left, 1);
        brakeLeft.whileHeld(new Brake());
    	 */
    	
    	//open1 = new JoystickButton (left,1);
    	//open1.whenPressed(EndGame());
    	
    	//open1.whenPressed(solenoid1.set(DoubleSolenoid.Value.kForward()));
    	
    	
    	/*solenoid1.set(DoubleSolenoid.Value.kForward);
    	solenoid1.set(DoubleSolenoid.Value.kReverse);
    	
    	solenoid2.set(DoubleSolenoid.Value.kForward);
    	solenoid2.set(DoubleSolenoid.Value.kReverse);
    	
    	solenoid3.set(DoubleSolenoid.Value.kForward);
    	solenoid3.set(DoubleSolenoid.Value.kReverse);
    	
    	solenoid4.set(DoubleSolenoid.Value.kForward);
    	solenoid4.set(DoubleSolenoid.Value.kReverse);*/
    }
    
   

	/**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
