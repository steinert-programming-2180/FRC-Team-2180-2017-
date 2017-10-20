package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveToBaseline extends Command {
	
	Timer timer;
	int pos;
	double SPEED, TIME_TO_DRIVE;
	public DriveToBaseline(int pos) {
		timer = new Timer();
		SPEED = 0.3;
		this.pos = pos;
		if (pos == 1 || pos == 3) {
			TIME_TO_DRIVE = (148/179.76)/SPEED;
		} else {
			TIME_TO_DRIVE = (60/179.76)/SPEED;
		}
		
	}
	
	protected void initialize() {
		timer.start();
	}
	
	protected void execute() {
		Robot.left1.set(-SPEED);
		Robot.left2.set(-SPEED);
		Robot.left3.set(-SPEED);
		Robot.right1.set(SPEED);
		Robot.right2.set(SPEED);
		Robot.right3.set(SPEED);	
	}
	
	protected boolean isFinished() {
		if (timer.get() > TIME_TO_DRIVE) {
			return true;
		}
		return false;
	}
	
	protected void end(){
		Robot.left1.set(0);
		Robot.left2.set(0);
		Robot.left3.set(0);
		Robot.right1.set(0);
		Robot.right2.set(0);
		Robot.right3.set(0);
		timer.stop();
		timer.reset();
	}
	
	protected void interrupted(){
		end();
	}

}
