package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlignGear extends Command {
	AnalogGyro gyro = Robot.gyro;
	
	public AlignGear() {
		
	}
	
	public void execute() {
		double width = SmartDashboard.getNumber("Width of reflective tape", 1);
		double centerOfTape = SmartDashboard.getNumber("Center of tape", 1); //camera code needs to be modified
		int centerOfFOV = 320;
		double ultraDis = SmartDashboard.getNumber("Ultra dis", 1);
		double tapeWidth = 1.375; //width in inches of our reflective tape
		
		double angle = Math.asin(((centerOfFOV - centerOfTape) * tapeWidth) / (width * ultraDis)) * (180 / Math.PI); //degrees
		
		gyro.reset();
		
		while (Math.abs(gyro.getAngle()) < Math.abs(angle)) { //check what the Gyroscope outputs when it loops counterclockwise
			if (angle < 0) {
				//move to the left
				Robot.left.set(-1);
				Robot.right.set(-1);
			} else if (angle > 0) {
				//move to the right
				Robot.left.set(1);
				Robot.right.set(1);
			} else {
				//stop driving, the robot's perfectly aligned!
				break;
			}
		}
		
	}
		
	protected boolean isFinished() {
		return true; //check this
	}
}

