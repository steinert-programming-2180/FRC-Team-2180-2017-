package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Seek1Command extends Command {
	double desiredAngle = 0;
	public Seek1Command(double desiredAngle) {
		this.desiredAngle = desiredAngle;
		SmartDashboard.putString("Last day of build season debug pls help", "Seek1Command constructor");
	}
	
	protected void initialize() {
		SmartDashboard.putString("Last day of build season debug pls help", "Seek1Command initialize");
	}
	
	protected void execute() {
		SmartDashboard.putString("Last day of build season debug pls help", "Seek1Command execute");
		SmartDashboard.putNumber("Desired Enc", desiredAngle);
		double error = Math.abs(desiredAngle-Robot.currentAngle);
		SmartDashboard.putNumber("Dist to Value", error);
		int direction = (int) Math.abs(desiredAngle-Robot.currentAngle);
		SmartDashboard.putNumber("Return Value", error*direction);
		Robot.seek1 = error*direction;
		
		if(Math.abs(Robot.seek1)>60){
			Robot.gearPickUp.set(Math.signum(Robot.seek1)*0.2);
		}
		
		
	}

	protected boolean isFinished() {
		return false;
	}
	
	protected void interrupted() {
		
	}
	
	protected void end() {
		
	}

}
