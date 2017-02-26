package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Seek2Command extends Command {
	double desiredAngle = 0;
	public Seek2Command(double desiredAngle) {
		this.desiredAngle = desiredAngle;
	}
	
	public void execute() {
		SmartDashboard.putNumber("Desired Enc", desiredAngle);
		double error = Math.abs(desiredAngle-Robot.currentAngle);
		SmartDashboard.putNumber("Dist to Value", error);
		int direction = (int) Math.abs(desiredAngle-Robot.currentAngle);
		SmartDashboard.putNumber("Return Value", error*direction);
		Robot.seek2 = error*direction;
		
		if(Math.abs(Robot.seek2)>60){
			Robot.gearPickUp.set(Math.signum(Robot.seek2)*0.2);
        }
	}

	protected boolean isFinished() {
		return false;
	}

}
