package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Loader extends Command {
	
	public Loader() {
		SmartDashboard.putString("Last day of build season debug pls help", "Loader constructor");	
	}
	
	protected void initialize(){
		SmartDashboard.putString("Last day of build season debug pls help", "Loader initialize");	
	}
	
	public void execute() {
		SmartDashboard.putString("Last day of build season debug pls help", "Loader execute");	
		Robot.loader.set(1.0);
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}

}
