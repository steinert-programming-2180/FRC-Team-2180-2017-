package org.usfirst.frc.team2180.robot.commands;

import org.usfirst.frc.team2180.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DecrementSpeed extends Command{

	public DecrementSpeed() {
		
	}
	
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		Robot.shooterTalon.set(Robot.shooterTalon.getSpeed() - 0.01);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
