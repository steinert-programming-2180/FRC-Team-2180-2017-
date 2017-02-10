package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static NetworkTable table;
	
	CameraServer camera;
	
	CANTalon right1 = new CANTalon(10);
	CANTalon right2 = new CANTalon(20);
	CANTalon right3 = new CANTalon(30);

	CANTalon left1 = new CANTalon(15);
	CANTalon left2 = new CANTalon(25);
	CANTalon left3 = new CANTalon(35);
	
	public static CANTalon loader = new CANTalon(23); //button
	public static CANTalon shooterMotor = new CANTalon(33); //need Hall effect (counter)
	
	public static CANTalon endGame = new CANTalon(17, 18);
	
	public static CANTalon gearPickUp = new CANTalon(29);
	
	//RobotDrive myRobot = new RobotDrive(left, right);
	public static Joystick leftDriver = new Joystick(0);
	public static Joystick rightDriver = new Joystick(1);
	public static Joystick leftPayload = new Joystick(2);
	public static Joystick rightPayload = new Joystick(3);
	
	public static JoystickButton zeroDegrees = new JoystickButton(rightDriver,1);
	public static JoystickButton fortyDegrees = new JoystickButton(rightDriver,2);
	public static JoystickButton ninetyDegrees = new JoystickButton(rightDriver,3);
	public static JoystickButton loaderUp = new JoystickButton(leftPayload,1);
	public static JoystickButton loaderDown = new JoystickButton(leftPayload,2);
	public static JoystickButton shooterButton = new JoystickButton(leftPayload,3);
	
	
	double volt0;
	String fs0;
	
	AnalogInput ultrasonicAI = new AnalogInput(0);
	
	Timer timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		camera = CameraServer.getInstance();
		camera.startAutomaticCapture();
		
		table = NetworkTable.getTable("datatable");
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		volt0 = ultrasonicAI.getAverageVoltage();
		
		fs0 = String.format("%6.1f ft", (volt0/(5.0/1024.0))*0.0328084);
		SmartDashboard.putString("DB/String 6", fs0);
		
		if (timer.get() < 2.0) {
			right1.set(1.0);
			right2.set(1.0);
			right3.set(1.0);
			
			left1.set(1.0); 
			left2.set(1.0);
			left3.set(1.0);
			
		} else if (timer.get() > 2.0 && timer.get() < 3.0){
			right1.set(1.0);
			right2.set(1.0);
			right3.set(1.0);
			
			left1.set(-1.0);
			left2.set(-1.0);
			left3.set(-1.0);
			
			
		}else if (timer.get() > 3.0 && timer.get() < 4.0 ){	
			
			right1.set(0.0);
			right2.set(0.0);
			right3.set(0.0);
			
			left1.set(0.0); // stops wheels
			left2.set(0.0);
			left3.set(0.0);
			
		}
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
		right1.set(rightDriver.getRawAxis(1));
		right2.set(rightDriver.getRawAxis(1));
		right3.set(rightDriver.getRawAxis(1));
		
		left1.set(leftDriver.getRawAxis(1)*-1);
		left2.set(leftDriver.getRawAxis(1)*-1);
		left3.set(leftDriver.getRawAxis(1)*-1);
		
		gearPickUp.set(rightPayload.getRawAxis(1));
		//endGame.setSpeed(payloadLeft.getRawAxis(1));
		endGame.set(leftPayload.getRawAxis(1));
		
		fortyDegrees.whenPressed(new Standard());
		ninetyDegrees.whenPressed(new Entry());
		zeroDegrees.whenPressed(new Placement());
		
		loaderUp.whenPressed(new Loader());
		loaderDown.whenPressed(new LoaderDown());
		
		shooterButton.whenPressed(new Shooter());
		
		
		volt0 = ultrasonicAI.getAverageVoltage();
		
		fs0 = String.format("%6.1f ft", (volt0/(5.0/1024.0))*0.0328084);
		SmartDashboard.putString("DB/String 6", fs0);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
