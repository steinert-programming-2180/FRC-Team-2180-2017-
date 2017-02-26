package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SeekHomeCommand extends Command {
	double desiredAngle = 0;
	public SeekHomeCommand(double desiredAngle) {
		this.desiredAngle = desiredAngle;
		SmartDashboard.putString("Last day of build season debug pls help", "SeekHomeCommand constructor");
	}
	
	protected void initialize() {
		SmartDashboard.putString("Last day of build season debug pls help", "SeekHomeCommand initialize");
	}
	
	public void execute() {
		SmartDashboard.putString("Last day of build season debug pls help", "SeekHomeCommand execute");
		SmartDashboard.putNumber("Desired Enc", desiredAngle);
		double error = Math.abs(desiredAngle-Robot.currentAngle);
		SmartDashboard.putNumber("Dist to Value", error);
		int direction = (int) Math.abs(desiredAngle-Robot.currentAngle);
		SmartDashboard.putNumber("Return Value", error*direction);
		Robot.seekHome = error*direction;
		
		if (Math.abs(Robot.seekHome)>60){
			Robot.gearPickUp.set(Math.abs(Robot.seekHome)*0.2);
		}
	}

	protected boolean isFinished() {
		return false;
	}

}
