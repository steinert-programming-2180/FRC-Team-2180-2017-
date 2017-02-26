package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Command {
	
	public Shooter() {
		SmartDashboard.putString("Last day of build season debug pls help", "Shooter constructor");
	}
	
	protected void initialize(){
		SmartDashboard.putString("Last day of build season debug pls help", "Shooter initialize");	
	}
	
	protected void execute() {
		SmartDashboard.putString("Last day of build season debug pls help", "Shooter execute");
		Robot.shooterMotor1.set(0.51);
		Robot.shooterMotor2.set(0.51);
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}

}
