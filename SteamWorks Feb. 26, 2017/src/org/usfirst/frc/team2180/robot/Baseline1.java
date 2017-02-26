package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Baseline1 extends Command {
	Timer timer = new Timer();
	
	public Baseline1() {
		timer.reset();
		timer.start();
	}
	
	public void execute() {
		if (timer.get() < 2){
		Robot.right1.set(0.7);
		Robot.right2.set(0.7);
		Robot.right3.set(0.7);
		
		Robot.left1.set(-0.7);
		Robot.left2.set(-0.7);
		Robot.left3.set(-0.7);
	
		}	else {
			Robot.right1.set(0.0);
			Robot.right2.set(0.0);
			Robot.right3.set(0.0);
			
			Robot.left1.set(0.0);
			Robot.left2.set(0.0);
			Robot.left3.set(0.0);
		}
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
