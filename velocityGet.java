
package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2180.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;

import org.usfirst.frc.team2180.robot.commands.ExampleCommand;
import org.usfirst.frc.team2180.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static int MoE = 10;
	public static OI oi;
	public static Joystick stick0 = new Joystick(0);
	public JoystickButton GearBox0 = new JoystickButton(stick0,4);
	public static JoystickButton GearBox1 = new JoystickButton(stick0,3);
	public JoystickButton GearBox2 = new JoystickButton(stick0,5);
	public static CANTalon gear = new CANTalon(10);
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	int currentPlace = 0;
	int curAng=0;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
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
		gear.set(0.4);
		SmartDashboard.putNumber("AnalogInRaw",(gear.getPulseWidthVelocity()));
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	//GHETTO FANTASTIC CODE START
	
	public void seekDir(int desiredAng){
		curAng=gear.getAnalogInRaw();
		if(curAng-desiredAng>2){
			gear.set(-0.05);
			SmartDashboard.putBoolean("curAng-desiredAng>2",true);
			SmartDashboard.putBoolean("curAng-desiredAng<-2",false);
			SmartDashboard.putBoolean("limit",false);
		}else if(curAng-desiredAng<-2){
			gear.set(0.05);
			SmartDashboard.putBoolean("curAng-desiredAng>2",false);
			SmartDashboard.putBoolean("curAng-desiredAng<-2",true);
			SmartDashboard.putBoolean("limit",false);
		}else{
			gear.set(0.0);
			SmartDashboard.putBoolean("curAng-desiredAng>2",false);
			SmartDashboard.putBoolean("curAng-desiredAng<-2",false);
			SmartDashboard.putBoolean("limit",false);
		}
		
		if(curAng>284){
			gear.set(0.05);
			SmartDashboard.putBoolean("curAng-desiredAng>2",false);
			SmartDashboard.putBoolean("curAng-desiredAng>2",false);
			SmartDashboard.putBoolean("limit",true);
		}
		
	}
	
}
