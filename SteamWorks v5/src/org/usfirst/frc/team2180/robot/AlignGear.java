package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlignGear extends Command {
	Timer timer = new Timer();
	double time = 0, timeToAlign = 0;
	
	public AlignGear() {	 
		
	}
	
	public void execute() {
		time = timer.get();
		timer.start();
		
		timeToAlign = SmartDashboard.getNumber("Arc length to traverse", 1) / 179.76 / 0.3;
		
		while (time < timeToAlign) {
			SmartDashboard.putNumber("Time to align", timeToAlign);
			if (SmartDashboard.getNumber("Client Message", 1) > 320) {
				//move left side
				Robot.left.set(0.3);
			} else if (SmartDashboard.getNumber("Client Message", 1) < 320) {
				//move right side
				Robot.right.set(0.3);
			} else {
				break;
			}
			time = timer.get();
		}
	}
	@Override
	protected boolean isFinished() {
		return false;
	}

}
