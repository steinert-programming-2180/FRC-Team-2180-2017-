package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TurnToFacePeg extends Command {
	
	Timer timer;
	int pos;
	final double SPEED, TIME_TO_DRIVE;
	public TurnToFacePeg(int pos) {
		this.pos = pos;
		timer = new Timer();
		SPEED = 0.5;
		TIME_TO_DRIVE = 0.65; //complete magic number-- check this!!!
	}
	
	protected void initialize() {
		timer.start();
	}
	
	protected void execute() {
		if (pos == 1) {
			Robot.left1.set(SPEED);
			Robot.left2.set(SPEED);
			Robot.left3.set(SPEED);
			Robot.right1.set(SPEED);
			Robot.right2.set(SPEED);
			Robot.right3.set(SPEED);
		} else if (pos == 3) {
			Robot.left1.set(-SPEED);
			Robot.left2.set(-SPEED);
			Robot.left3.set(-SPEED);
			Robot.right1.set(-SPEED);
			Robot.right2.set(-SPEED);
			Robot.right3.set(-SPEED);
		}
			
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
