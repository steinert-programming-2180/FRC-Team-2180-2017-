package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static CANTalon right1 = new CANTalon (10);
	public static CANTalon right2 = new CANTalon (20);
	public static CANTalon right3 = new CANTalon (30);
	
	public static CANTalon left1 = new CANTalon (15);
	public static CANTalon left2 = new CANTalon (25);
	public static CANTalon left3 = new CANTalon (35);
	
	public static CANTalon left = new CANTalon(15, 25, 35);
	public static CANTalon right = new CANTalon(10, 20, 30);
	
	public static CANTalon loader = new CANTalon (23);
	
	public static CANTalon shooterMotor1 = new CANTalon(24);
	public static CANTalon shooterMotor2 = new CANTalon(27);
	
	CANTalon endGameMotor = new CANTalon(29);
	CANTalon endGameMotor2 = new CANTalon(39);
	
	public static CANTalon gearPickUp = new CANTalon(34);
	
	public static Joystick leftDriver = new Joystick(0);
	public static Joystick rightDriver = new Joystick(1);
	public static Joystick payload = new Joystick(2);
	
	JoystickButton startServerButton = new JoystickButton(payload,1);
	
	double shooterSpeed, desiredSpeed;
	
	Timer timer = new Timer();
	
	double volts0;
	String fs0;
	double time = 0;
	
	public static int currentAngle = 0;
	public static double seek1 = 0;	
	public static double seek2 = 0;
	public static double seekHome = 0;
	UsbCamera camera;
	
	public static JoystickButton seek1But = new JoystickButton(payload, 3), 
								 seek2But = new JoystickButton(payload, 4), 
								 seekHomeBut = new JoystickButton(payload, 5),
								 shooterButton = new JoystickButton(payload, 1),
								 loaderButton = new JoystickButton(payload, 2),
								 alignGearButton = new JoystickButton(leftDriver, 1),
								 moveBackSlightly = new JoystickButton(leftDriver, 5);
	
	public static AnalogInput ultrasonicAI = new AnalogInput(0);
	
	Command autonomousCommand;
	SendableChooser<Command> autoChooser;
	
	public static AnalogGyro gyro = new AnalogGyro(1);
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
//		new Thread(() -> {
//			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//			camera.setResolution(640, 480);
//			CvSink cvSink = CameraServer.getInstance().getVideo();
//			CvSource outputStream = CameraServer.getInstance().putVideo("Frame", 640, 480);
//			
//			Mat source = new Mat();
//			
//			while (!Thread.interrupted()) {
//				cvSink.grabFrame(source);
//				//check point values while testing
//				Imgproc.line(source, new Point(300, 0), new Point(300, 480), new Scalar(0, 255, 0), 2);
//				Imgproc.line(source, new Point(340, 0), new Point(340, 480), new Scalar(0, 255, 0), 2);
//				outputStream.putFrame(source);
//			}
//		}).start();
		
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		//shooterMotor1.changeControlMode(CANTalon.TalonControlMode.Follower);
		//shooterMotor1.set(shooterMotor2.getDeviceID());
		
		desiredSpeed = 1000;
		
		gearPickUp.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		shooterMotor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		autonomousCommand = new BaseLine();
		currentAngle = (((int)gearPickUp.getPulseWidthPosition())&0xFFF);
		shooterSpeed = shooterMotor2.getPulseWidthVelocity() & 0xFFF;
		seek1 = getSeekSped(2163);
		seek2 = getSeekSped(1500);
		seekHome = getSeekSped(2920);
		
		autoChooser = new SendableChooser<Command>();
		autoChooser.addDefault("Alliance 2 Peg 2", new Pos2AutonGear());
		autoChooser.addObject("Alliance 1 Peg 1", new Alliance1Peg1());
		autoChooser.addObject("Alliance 3 Peg 3", new Alliance3Peg3());
		autoChooser.addObject("Spot 3 Face Gear", new Spot3FaceGear());
		autoChooser.addObject("Spot 1 Face Gear", new Spot1FaceGear());
//		autoChooser.addObject("Baseline 1", new Baseline1());
//		autoChooser.addObject("Baseline 3", new Baseline3());
		
//		seek1But.whenPressed(new Seek1Command(2075));
//		seek1But.whenPressed(new Seek2Command(1414));
//		seek1But.whenPressed(new SeekHomeCommand(2800));
//		shooterButton.whileHeld(new Shooter());
//		loaderButton.whileHeld(new Loader());
		
//		alignGearButton.whenPressed(new AlignGearTeleOp());
		
		
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
		autonomousCommand.cancel();
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//gearPickUp.set(payload.getRawAxis(1));
		
		volts0 = ultrasonicAI.getAverageVoltage(); //UltraSonic Sensor
		fs0 = String.format("%6.1f ft", (volts0/(5.0/1024.0))*0.0328884);
		SmartDashboard.putNumber("Ultra distance", volts0/(5.0/1024.0)*0.0328884*12);
		SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
		
		currentAngle = (((int)gearPickUp.getPulseWidthPosition())&0xFFF); //gearPickUp
		SmartDashboard.putNumber("Current Enc", currentAngle);
		
		shooterSpeed = shooterMotor2.getPulseWidthVelocity() & 0xFFF;
		
		SmartDashboard.putNumber("Shooter speed", shooterSpeed);
		
		
		right1.set(rightDriver.getRawAxis(1)*-1);
		right2.set(rightDriver.getRawAxis(1)*-1);
		right3.set(rightDriver.getRawAxis(1)*-1);
		
		left1.set(leftDriver.getRawAxis(1));
		left2.set(leftDriver.getRawAxis(1));
		left3.set(leftDriver.getRawAxis(1));
		
		endGameMotor.set(payload.getRawAxis(1));
		endGameMotor2.set(payload.getRawAxis(1) * -1);
	
		if (payload.getRawButton(1)){
			shooterMotor1.set(0.6911);
			shooterMotor2.set(0.6911);
//			int pewpewspeed = shooterMotor2.getPulseWidthVelocity() & 0xFFF;
//			if (pewpewspeed < 3800) {
//				shooterMotor1.set(1);
//				shooterMotor2.set(1);
//			} else if (pewpewspeed > 3800) {
//				shooterMotor1.set(0);
//				shooterMotor2.set(0);
//			}
		} else {
			shooterMotor1.set(0);
			shooterMotor2.set(0);
		}
		
		if (leftDriver.getRawButton(5)) {
			Timer timer = new Timer();
			timer.start();
			
			while (timer.get() < 0.1) {
				left1.set(0.3);
				left2.set(0.3);
				left3.set(0.3);
				right1.set(-0.3);
				right2.set(-0.3);
				right3.set(-0.3);
			}
			
			timer.stop();
			timer.reset();
		}
	
	    if(payload.getRawButton(2)){
			loader.set(1.0);
		} else{
			loader.set(0.0);
		}
	    {
	    if(payload.getRawButton(4)){
	    	gearPickUp.set(0.2);
	    }
	    else if(payload.getRawButton(3)){
	    	gearPickUp.set(-0.2);
	    }
	    else if(payload.getRawButton(5)){
	    	gearPickUp.set(-1.0);
	    }
	    else if (payload.getRawButton(7)) {
	    	if (SmartDashboard.getNumber("Current Enc", 1) < 2920) {
	    		//while (SmartDashboard.getNumber("Current Enc", 1) < 2920) {
		    		gearPickUp.set(0.5);
		    	//}
	    	} else if (SmartDashboard.getNumber("Current Enc", 1) > 2920) {
	    		//while (SmartDashboard.getNumber("Current Enc", 1) > 2920) {
		    		gearPickUp.set(-0.5);
		    	//}
	    	}
//	    	while (timer.get() < 0.5) {
//	    		
//	    	}
	    	
	    	
	    	
	    	
	    }
	    else if (payload.getRawButton(10)) {
	    	timer.reset();
	    	timer.start();
	    	while (timer.get() < 1.1) {
	    		gearPickUp.set(-0.5);
	    	}
	    	timer.stop();
	    	timer.reset();
	    	timer.start();
	    	while (timer.get() < 0.5) {
	    		left1.set(0.3);
	    		left2.set(0.3);
	    		left3.set(0.3);
	    		right1.set(-0.3);
	    		right2.set(-0.3);
	    		right3.set(-0.3);
	    	}
	    	timer.stop();
	    	timer.reset();
	    	left1.set(0);
    		left2.set(0);
    		left3.set(0);
    		right1.set(0);
    		right2.set(0);
    		right3.set(0);
    		gearPickUp.set(0);
	    }
	    else {
	    	gearPickUp.set(0.0);
	    }
	    }
		/*if(payload.getRawButton(6)){
//			if((seek1)>60){
//				gearPickUp.set(Math.signum(seek1)*(0.2));
//			}else{
//			gearPickUp.set(0.0);
//			}
			//while (currentAngle != 1500) {
				if (currentAngle > 1500) {
					gearPickUp.set(-0.4);
				} else if (currentAngle < 1500) {
					gearPickUp.set(0.4);
				} else {
					gearPickUp.set(0);
				}
			//}
			
			
		}else if(payload.getRawButton(11)){
//			if((seek2)>5)
//				gearPickUp.set(Math.signum(seekHome)*(0.2));
//			
//			else{
//				gearPickUp.set(0.0);
//			}
			//while (currentAngle != 2163) {
				if (currentAngle > 2163) {
					gearPickUp.set(-0.4);
				} else if (currentAngle < 2163) {
					gearPickUp.set(0.4);
				} else {
					gearPickUp.set(0);
				}
			//}
			
		}else if (payload.getRawButton(7)){
//			if ((seekHome)>60){
//				gearPickUp.set(Math.signum(seek2)*(0.2));
//			}else{
//			gearPickUp.set(0.0);
//		}	
			//while (currentAngle != 2939) {
				if (currentAngle > 2939) {
					gearPickUp.set(-0.4);
				} else if (currentAngle < 2939) {
					gearPickUp.set(0.4);
				} else {
					gearPickUp.set(0);
				}
			//}
			
		}else{
			gearPickUp.set(0.0);
		}*/

	
	}
	// High Position 2939
	// p1 2163
	// p2 1500

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	@Override
	public void disabledPeriodic() {
		
	}
	
	public double getSeekSped (double desiredAngle) {
		SmartDashboard.putNumber("Desired Enc", desiredAngle);
		double error = Math.abs(currentAngle-desiredAngle);
		SmartDashboard.putNumber("Dist to Value", error);
		int direction = (int) Math.signum(currentAngle-desiredAngle);
		SmartDashboard.putNumber("Return Value", error*direction);
		return (error*direction);
	}
}