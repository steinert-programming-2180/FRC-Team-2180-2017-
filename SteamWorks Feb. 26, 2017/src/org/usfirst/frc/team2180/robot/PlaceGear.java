package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class PlaceGear extends Command {

	public PlaceGear() {
		
	}
	
	public void execute() {
		Timer timer = new Timer();
		timer.reset();
		int pos = Robot.gearPickUp.getPulseWidthPosition() & 0xFFF;
		while (pos > 2506) {
			Robot.gearPickUp.set(-0.5);
		}
		
		timer.start();
		while(timer.get() < 0.25) {
			Robot.left.set(0.5);
			Robot.right.set(-0.5);
		}
		timer.stop();
		timer.reset();
		
		while (pos > 1687) {
			Robot.gearPickUp.set(-0.5);
		}
		
		timer.start();
		while(timer.get() < 1) {
			Robot.left.set(-1);
			Robot.right.set(1);
		}	
	}
	
	protected boolean isFinished() {
		return true;
	}

}
