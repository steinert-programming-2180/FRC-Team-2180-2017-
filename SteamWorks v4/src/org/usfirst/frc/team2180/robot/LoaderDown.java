package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.Command;

public class LoaderDown extends Command {

public LoaderDown(){
		
	}
	
	protected void initalize(){
		
	}
	
	protected void executed(){
		Robot.loader.set(-0.5);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}

}
