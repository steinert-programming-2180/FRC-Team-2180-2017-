
package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
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

	public static OI oi;
	public AnalogInput pot;
	public int pos;
	public int PresentPosLF, PresentPosRF, PresentPosLR, PresentPosRR;
	double volts0;
	String fs0, fs1, fs2, fs3, fs4; 
	public CANTalon drive1,drive2,drive3,drive4;
	public CANTalon steer1,steer2,steer3,steer4;
	public Joystick left,right;;
	private static final int kUltrasonicPort = 0;
	private AnalogInput ultrasonicAI = new AnalogInput(kUltrasonicPort);
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		pot= new AnalogInput(1);
		drive1= new CANTalon(10);
		drive2= new CANTalon(20);
		drive3= new CANTalon(30);
		drive4= new CANTalon(40);
		steer1= new CANTalon(11);
		steer2= new CANTalon(21);
		steer3= new CANTalon(31);
		steer4= new CANTalon(41);
		left= new Joystick(0);
		right= new Joystick(1);
		
		
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		pos= pot.getAverageValue();
		volts0 = ultrasonicAI.getAverageVoltage();
		
		steer1.set(right.getRawAxis(0)*.5);
		steer2.set(right.getRawAxis(0)*.5);
		steer3.set(right.getRawAxis(0)*.5);
		steer4.set(right.getRawAxis(0)*.5);
		drive1.set(left.getRawAxis(1)*.5);
		drive2.set(left.getRawAxis(1)*.5);
		drive3.set(left.getRawAxis(1)*.5);
		drive4.set(left.getRawAxis(1)*.5);
		
		PresentPosLF=0;
		PresentPosRF=0;
		PresentPosLR=0;
		PresentPosRR=0;
		
		PresentPosLF = (int)((double)steer2.getAnalogInRaw()*0.36);
		PresentPosRF = (int)((double)steer1.getAnalogInRaw()*0.36);
		PresentPosLR = (int)((double)steer3.getAnalogInRaw()*0.36);
		PresentPosRR = (int)((double)steer4.getAnalogInRaw()*0.36);
		
		fs0 = String.format("%6.1f ft ", (volts0/(5.0/1024.0))*0.0328084);
		fs1 = String.format("left Front %d deg", PresentPosLF);
		fs2 = String.format("left Rear %d deg", PresentPosLR);
		fs3 = String.format("Right Front %d deg", PresentPosRF);
		fs4 = String.format("Right Rear %d deg", PresentPosRR);
		
		SmartDashboard.putString("DB/String 3", fs1);
		SmartDashboard.putString("DB/String 4", fs2);
		SmartDashboard.putString("DB/String 8", fs3);
		SmartDashboard.putString("DB/String 9", fs4);
		SmartDashboard.putString("DB/String 6", fs0);
		SmartDashboard.putNumber("position from pot", pos);
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
