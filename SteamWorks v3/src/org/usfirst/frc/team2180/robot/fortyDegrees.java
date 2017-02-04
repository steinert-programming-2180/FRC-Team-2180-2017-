package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;

public class fortyDegrees extends Command{
	
	public fortyDegrees(){
		
	}
	
	protected void initalize(){
		
	}
	
	protected void executed(){
		Robot.shooter.set(0.5);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}

}
