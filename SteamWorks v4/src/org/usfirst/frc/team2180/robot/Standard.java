package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Standard extends Command {
	
	public Standard (){
		CANTalon gearPickUp = new CANTalon (29);
		
		String fs1;
		int PresentPos;
		
		PresentPos = 0;
		
		PresentPos = (int)((double)gearPickUp.getAnalogInRaw()*0.36);
		
		fs1 = String.format("Gear %d deg", PresentPos);
		
		SmartDashboard.putString("DB/String 0", fs1);
	}
	
	protected void initialize(){
		
		
	}
	
	protected void execute(){
		
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
	

	//@Override
	//protected boolean isFinished() {
		// TODO Auto-generated method stub
		//return false;
}


