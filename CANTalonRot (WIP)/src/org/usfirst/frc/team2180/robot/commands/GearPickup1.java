package org.usfirst.frc.team2180.robot.commands;


import org.usfirst.frc.team2180.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

public class GearPickup1 extends Command {
	
	int ang=0;
	public GearPickup1(){}
	
	protected void initialize(){
		ang=128;
	}
	
	protected void execute(){
		while (true) {
			Robot.gear.set(Robot.seekDir(Robot.gear.getAnalogInRaw(),ang,0.3));
		}
		
	}
	
	protected boolean isFinished(){
		boolean fin;
		if(Robot.iformat(Robot.gear.getAnalogInRaw()+Robot.MoE)>ang){
			fin=false;
		}else if(Robot.iformat(Robot.gear.getAnalogInRaw()+Robot.MoE)>ang){
			fin=false;
		}else{
			fin=true;
		}
		return false;
		
	}
	
	protected void end(){}
	
	protected void interrupted(){}
}