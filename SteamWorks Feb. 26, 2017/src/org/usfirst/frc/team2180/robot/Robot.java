package org.usfirst.frc.team2180.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
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
	
	public static CANTalon loader = new CANTalon (24);
	
	public static CANTalon shooterMotor1 = new CANTalon(17);
	public static CANTalon shooterMotor2 = new CANTalon(27);
	
	CANTalon endGameMotor = new CANTalon(29);
	CANTalon endGameMotor2 = new CANTalon(39);
	
	public static CANTalon gearPickUp = new CANTalon(33);
	
	Joystick leftDriver = new Joystick(0);
	Joystick rightDriver = new Joystick(1);
	public static Joystick payload = new Joystick(2);
	
	JoystickButton startServerButton = new JoystickButton(payload,1);
	
	Timer timer = new Timer();
	
	double volts0;
	String fs0;
	double time = 0;
	
	public static int currentAngle = 0;
	public static double seek1 = 0;	
	public static double seek2 = 0;
	public static double seekHome = 0;
	
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
		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(640, 480);
			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("Frame", 640, 480);
			
			Mat source = new Mat();
			
			while (!Thread.interrupted()) {
				cvSink.grabFrame(source);
				//check point values while testing
				Imgproc.line(source, new Point(300, 0), new Point(300, 480), new Scalar(0, 255, 0), 2);
				Imgproc.line(source, new Point(340, 0), new Point(340, 480), new Scalar(0, 255, 0), 2);
				outputStream.putFrame(source);
			}
		}).start();
		
		
		gearPickUp.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		autonomousCommand = new Spot1DropGearA();
		
		currentAngle = (((int)gearPickUp.getPulseWidthPosition())&0xFFF);
		
		seek1 = getSeekSped(2506);
		seek2 = getSeekSped(1687);
		seekHome = getSeekSped(3045);
		
		autoChooser = new SendableChooser<Command>();
		
		autoChooser.addDefault("Spot 1 Drop Gear A", new Spot1DropGearA());
		autoChooser.addObject("Spot 2 Drop Gear B", new Spot2DropGearB());
		autoChooser.addObject("Spot 3 Drop Gear C", new Spot3DropGearC());
		autoChooser.addObject("Baseline 1", new Baseline1());
		autoChooser.addObject("Baseline 3", new Baseline3());
		
//		seek1But.whenPressed(new Seek1Command(2075));
//		seek1But.whenPressed(new Seek2Command(1414));
//		seek1But.whenPressed(new SeekHomeCommand(2800));
//		shooterButton.whileHeld(new Shooter());
//		loaderButton.whileHeld(new Loader());
		
		
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
		gyro.reset();
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
		SmartDashboard.putString("DB/String 6", fs0);
		
		currentAngle = (((int)gearPickUp.getPulseWidthPosition())&0xFFF); //gearPickUp
		SmartDashboard.putNumber("Current Enc", currentAngle);
		
		right1.set(rightDriver.getRawAxis(1)*-1);
		right2.set(rightDriver.getRawAxis(1)*-1);
		right3.set(rightDriver.getRawAxis(1)*-1);
		
		left1.set(leftDriver.getRawAxis(1));
		left2.set(leftDriver.getRawAxis(1));
		left3.set(leftDriver.getRawAxis(1));
		
		endGameMotor.set(payload.getRawAxis(1));
		endGameMotor2.set(payload.getRawAxis(1) * -1);
	
		if (payload.getRawButton(1)){
			shooterMotor1.set(0.55);
			shooterMotor2.set(0.55);
		} else {
			shooterMotor1.set(0);
			shooterMotor2.set(0);
		}
	
	    if(payload.getRawButton(2)){
			loader.set(1.0);
		} else{
			loader.set(0.0);
		}
	
		
		if(payload.getRawButton(3)){
			if((seek1)>60){
				gearPickUp.set(Math.signum(seek1)*(-0.2));
			}else{
			gearPickUp.set(0.0);
			}
		}else if(payload.getRawButton(4)){
			if((seek2)>60)
				gearPickUp.set(Math.signum(seek2)*(0.2));
			else{
				gearPickUp.set(0.0);
			}
		}else if (payload.getRawButton(5)){
			if ((seekHome)>60){
				gearPickUp.set(Math.signum(seekHome)*(-0.2));
			}else{
			gearPickUp.set(0.0);
		}	
		}else{
			gearPickUp.set(0.0);
		}

	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public double getSeekSped (double desiredAngle) {
		SmartDashboard.putNumber("Desired Enc", desiredAngle);
		double error = Math.abs(desiredAngle-currentAngle);
		SmartDashboard.putNumber("Dist to Value", error);
		int direction = (int) Math.signum(desiredAngle-currentAngle);
		SmartDashboard.putNumber("Return Value", error*direction);
		return (error*direction);
	}
}
