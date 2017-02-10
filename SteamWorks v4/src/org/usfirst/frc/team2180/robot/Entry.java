package org.usfirst.frc.team2180.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Entry extends Command {
	public CANTalon gearPickUp;
	String fs1;
	int PresentPos;
		
	public Entry (){
		gearPickUp = new CANTalon (29);
		PresentPos = 0;
	}
	
	protected void initialize(){
		
		
	}
	
	protected void execute(){
		PresentPos = (int)((double)gearPickUp.getAnalogInRaw()*0.36);
		
		fs1 = String.format("Gear %d deg", PresentPos);
		
		SmartDashboard.putString("DB/String 0", fs1);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
}
