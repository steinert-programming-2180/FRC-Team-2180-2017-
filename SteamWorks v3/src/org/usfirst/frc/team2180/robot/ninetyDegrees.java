package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;

public class ninetyDegrees extends Command{
	
	public ninetyDegrees(){
		
	}
	
	protected void initalize(){
		
	}
	
	protected void executed(){
		Robot.shooter.set(.8);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}

}
