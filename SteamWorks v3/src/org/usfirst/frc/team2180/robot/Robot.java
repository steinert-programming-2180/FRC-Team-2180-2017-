package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	CameraServer camera;
	CANTalon right1 = new CANTalon(10);
	CANTalon right2 = new CANTalon(20);
	CANTalon right3 = new CANTalon(30);
	
	CANTalon left1 = new CANTalon(15);
	CANTalon left2 = new CANTalon(25);
	CANTalon left3 = new CANTalon(35);
	
	public static CANTalon loader = new CANTalon(23); //button
	public static CANTalon shooter = new CANTalon(33); //need Hall effect (counter)
	
	public static CANTalon endGame = new CANTalon(40); //Joystick
	
	public static CANTalon gearPickUp = new CANTalon(29); //need encoder
	
	
	//RobotDrive myRobot = new RobotDrive(0, 1);
	public static Joystick left = new Joystick(0);
	public static Joystick right = new Joystick(1);
	public static Joystick payloadLeft = new Joystick(2);
	public static Joystick payloadRight = new Joystick(3);
	
	public static JoystickButton fortyDegrees = new JoystickButton(left,1);
	public static JoystickButton ninetyDegrees = new JoystickButton(right,1);
	//Timer timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		camera = CameraServer.getInstance();
		camera.startAutomaticCapture();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		//timer.reset();
		//timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		/*if (timer.get() < 2.0) {
			myRobot.drive(-0.5, 0.0); // drive forwards half speed
		} else {
			myRobot.drive(0.0, 0.0); // stop robot
		}*/
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		right1.set(right.getRawAxis(1));
		right2.set(right.getRawAxis(1));
		right3.set(right.getRawAxis(1));
		
		left1.set(left.getRawAxis(1)*-1);
		left2.set(left.getRawAxis(1)*-1);
		left3.set(left.getRawAxis(1)*-1);
		
		gearPickUp.set(payloadRight.getRawAxis(1));
		endGame.set(payloadLeft.getRawAxis(1));
		
		fortyDegrees.whenPressed(new fortyDegrees());
		ninetyDegrees.whenPressed(new ninetyDegrees());
		//SmartDashboard.putData("Auto Mode", chooser);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
