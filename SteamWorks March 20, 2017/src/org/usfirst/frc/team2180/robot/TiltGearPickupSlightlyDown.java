package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TiltGearPickupSlightlyDown extends Command {
	
	Timer timer;
	int pos, stage;
	final double TIME_TO_MOVE_STAGE_1, TIME_TO_MOVE_STAGE_2;
	public TiltGearPickupSlightlyDown(int stage) {
		timer = new Timer();
		pos = 0;
		this.stage = stage;
		TIME_TO_MOVE_STAGE_1 = 0.4;
		TIME_TO_MOVE_STAGE_2 = 0.65;
	}
	
	protected void initialize() {
		timer.start();
		pos = Robot.gearPickUp.getPulseWidthPosition() & 0xFFF;
	}
	
	protected void execute() {
		if (stage == 1) {
			Robot.gearPickUp.set(-0.3);
		} else if (stage == 2) {
			Robot.gearPickUp.set(-0.5);
		}
		
		pos = Robot.gearPickUp.getPulseWidthPosition() & 0xFFF;	
		SmartDashboard.putNumber("Pos", pos);
			
	}
	
	protected boolean isFinished() {
		if (stage == 1 && timer.get() > TIME_TO_MOVE_STAGE_1) {
			return true;
		} else if (stage == 2 && timer.get() > TIME_TO_MOVE_STAGE_2) {
			return true;
		}
		return false;
	}
	
	protected void end(){
		timer.stop();
		timer.reset();
		Robot.gearPickUp.set(0);
		timer.start();
		while (timer.get() < 0.5) {

		}
		timer.stop();
		timer.reset();
		
	}
	
	protected void interrupted(){
		end();
	}
}
