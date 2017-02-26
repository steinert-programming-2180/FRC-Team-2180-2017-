package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GetToPeg extends Command {
	
	int pos;
	public GetToPeg(int pos) {
		this.pos = pos;
	}
	
	public void execute() {
		double revs = 0;
		Timer timer = new Timer();
		timer.reset();
		revs = 93.3/(4*Math.PI);
		
		SmartDashboard.putNumber("Revs to make", revs);
		
		timer.start();
		
		if (pos == 1 || pos == 3) {
			revs = 93.3/(4*Math.PI);
			while (timer.get() <= revs * (4*Math.PI)/179.76) {
				Robot.left.set(1);
				Robot.right.set(-1);
			}
		} else if (pos == 2) {
			revs = 63.3/(4*Math.PI);
			while (timer.get() <= revs * (4*Math.PI)/179.76) {
				Robot.left.set(1);
				Robot.right.set(-1);
			}
		}
		 
		
		//make sure to test spot 2 gear B moving straight, it may hit the peg hard
		
		timer.stop();
		timer.reset();
		
		Robot.gyro.reset();
		
		if (pos == 1) {
			while (Robot.gyro.getAngle() < 66.84) {
				//move right to be perpendicular with side of airship
				Robot.left.set(1);
				Robot.right.set(1);
			}
		} else if (pos == 3) {
			while (Robot.gyro.getAngle() > -66.84) { //check conditional while testing
				//move left to be perpendicular with side of airship
				Robot.left.set(-1);
				Robot.right.set(-1);
			}
		}
		
	}
	
	protected boolean isFinished() {
		return true;
	}

}
