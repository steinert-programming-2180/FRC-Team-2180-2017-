package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends IterativeRobot {
		
		CameraServer camera;
		
		CANTalon right1 = new CANTalon(10);
		CANTalon right2 = new CANTalon(20);
		CANTalon right3 = new CANTalon(30);

		public static CANTalon left = new CANTalon(15, 25, 35);
		public static CANTalon right = new CANTalon(10, 20, 30);
		
		CANTalon left1 = new CANTalon(15);
		CANTalon left2 = new CANTalon(25);
		CANTalon left3 = new CANTalon(35);
		
		public static CANTalon loader = new CANTalon(24); //button
		public static CANTalon shooterMotor = new CANTalon(17); //need Hall effect (counter)
		public static CANTalon shooterMotor2 = new CANTalon(27);
		
		public static CANTalon endGameMotor = new CANTalon(29);
		public static CANTalon endGameMotor2 = new CANTalon(39);
		
		public static CANTalon gearPickUp = new CANTalon(33);
		
		//RobotDrive myRobot = new RobotDrive(left, right);
		public static Joystick leftDriver = new Joystick(0);
		public static Joystick rightDriver = new Joystick(1);
		public static Joystick payload = new Joystick(2);
		//public static Joystick rightPayload = new Joystick(3);
		
		//public static JoystickButton zeroDegrees = new JoystickButton(rightDriver,1);
		//public static JoystickButton fortyDegrees = new JoystickButton(rightDriver,2);
		//public static JoystickButton ninetyDegrees = new JoystickButton(rightDriver,3);
		//public static JoystickButton loaderUp = new JoystickButton(leftPayload,1);
		//public static JoystickButton loaderDown = new JoystickButton(leftPayload,2);
		//public static JoystickButton shooterButton = new JoystickButton(leftPayload,3);
		//public static JoystickButton startClientButton = new JoystickButton(leftPayload, 4);
		JoystickButton startServerButton = new JoystickButton(leftDriver, 4);
		
		
		double volt0;
		String fs0;
		double time = 0;
		
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
		
			//startClientButton.whenPressed(new StartClient());
			//startServerButton.whenPressed(new StartServer());
			
			
			gearPickUp.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
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
			Scheduler.getInstance().run();
			Scheduler.getInstance().add(new StartClient());
			Scheduler.getInstance().add(new StartServer());
			Scheduler.getInstance().add(new AlignGear());
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
			
			{
				if(payload.getRawButton(1)){
					shooterMotor.set(0.51);
					shooterMotor2.set(0.51);
				}else{
					shooterMotor.set(0.0);
					shooterMotor2.set(0.0);
				}
				
				{
					if(payload.getRawButton(2)){
						loader.set(1.0);
					}else{
						loader.set(0.0);
					}
				}
				
				{
					if(payload.getRawButton(5)){
						gearPickUp.set(1.0);
					}else{
						gearPickUp.set(0.0);
					}
				}
				
				{
					if(payload.getRawButton(6)){
						gearPickUp.set(0.5);
					}else{
						gearPickUp.set(0.0);
					}
				}
				
				{
					if(payload.getRawButton(7)){
						gearPickUp.set(0.3);
					}else{
						gearPickUp.set(0.0);
					}
				}
			
			endGameMotor.set(payload.getRawAxis(1));
			endGameMotor2.set(payload.getRawAxis(1)*-1);
		
			
			volt0 = ultrasonicAI.getAverageVoltage();
			
			fs0 = String.format("%6.1f ft", (volt0/(5.0/1024.0))*0.0328084);
			SmartDashboard.putString("DB/String 6", fs0);
			
			SmartDashboard.putNumber("AnalogInRaw Velocity", (((int)gearPickUp.getSpeed()) & 0xFFF));
			SmartDashboard.putNumber("AnalogInRaw Position", (((int)gearPickUp.getPosition()) & 0xFFF) * 0.087890625);
			}
			
			
			
		}

		/**
		 * This function is called periodically during test mode
		 */
		@Override
		public void testPeriodic() {
			LiveWindow.run();
		}
	}

