package org.usfirst.frc.team2180.robot;
/* Team 2180 (Zero Gravity) 2017 
 * Max N., Ravi "Fully Automated Luxury Gay Space Communism" D'Elia
  
 *\\Requires an absolute encoder and motor wired to CANTalon.\\
  
  Class creates simple means for a motor shaft to seek out a certain angle.
  
 * LE INTRUCTIONS:
  
 * Creation--
  NIV Gen 1:31-2 - 2:1-4
  
 * Object Creation--
  
	>CANTalonRot spinyThing = new CANTalonRot(CANTalon hostCANTalonCANTalon,int offset,int MoE)
 
  Where 'offset' is the offset from the desired zero (in Encoder units-- 0 to 1023)
  Where 'MoE' is the margin of error on the motor's angle, also in encoder units. 
  DO NOT SET MoE TO ZERO, extreme hysteresis WILL occur.
  
 *Why is this better than a normal CANTalon object?
  This enables motors to seek a certain angle, more or less.
  For simple robots parts like a turret or dropper, this greatly simplifies the accurate rotating progress.
  
 *Explanation of each method:
	
	ALL GETTERS:
	>CANTalon.getCANTalon()
		Returns the CANTalon you assigned this to
	>CANTalon.getRot()
		Returns the encoder value (0-1023), adjusted with offset
	>CANTalon.getRawRot()
		Returns the encoder value (0-1023) alone
	
	ANY OTHER VARIABLE MUST BE ACCESED THROUGH
	>CANTalon.[variable name]
 
 	ALL SETTERS:
 	>CANTalon.setPow(double motorSped)
 		No, sped isn't a typo, it looks cool
	 	Replacement for CANTalon.set(double speed)
 	>CANTalon.setAng(double Angle, double motorSped)
 		Seeks the rough conversion of the 360 Degrees circle unit.
 		I.E. setting angle to 90.0 and motorSped to 0.3 will make the motor shaft seek 90 degrees (plus/minus the MoE) at 3/10ths sped.                                                                                                                                                                                                                                                                                  
    >CANTalon.setAng(int EncoderVal, double motorSped)
    	Seeks the absolute value of the encoder. 
    	I.E. setting EncoderVal to 256 and motorSped to 0.3 will make the motor shaft seek 90 degrees (plus/minus the MoE) at 3/10ths sped.
    
    THE UPDATE
    	Updates the rotation whenever an (important) meathod is called.
    	It's private.
    	Dun worry bout it.
 */

import com.ctre.CANTalon;
import org.usfirst.frc.team2180.robot.ClosedSystem;

public class CANTalonRot {
	private CANTalon hostCANTalon;
	public int offset;
	private int actualRot;
	public int MoE;
	private ClosedSystem adjustedRot = new ClosedSystem(0, 0, 1023);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public CANTalonRot(CANTalon hostCANTalon){
		this.hostCANTalon=hostCANTalon;
		updateRot();
	}
	
	public CANTalonRot(CANTalon hostCANTalon, int offset){
		this.hostCANTalon=hostCANTalon;
		this.offset=offset;
	}
	
	public CANTalonRot(CANTalon hostCANTalon, int offset,int MoE){
		this.hostCANTalon=hostCANTalon;
		this.offset=offset;
		this.MoE=MoE;
	}
	
	//Setters
	
	public void setPow(double motorSped){
		updateRot();
		
		hostCANTalon.set(motorSped);
	}
	
	public void setAng(double Angle,double motorSped){
		updateRot();
		
		ClosedSystem EncIdeal = new ClosedSystem((int)(Angle*0.3515625),0,1023);

		if(this.adjustedRot.intMath(this.adjustedRot.intGet()+this.MoE)>EncIdeal.intGet()){
			this.hostCANTalon.set(motorSped);
		}else if(this.adjustedRot.intMath(this.adjustedRot.intGet()-this.MoE)<EncIdeal.intGet()){
			this.hostCANTalon.set(-motorSped);
		}else{
			this.hostCANTalon.set(0.0);
		}	
	}
	
	public void setAng(int EncoderVal,double motorSped){
		updateRot();
		
		ClosedSystem EncIdeal = new ClosedSystem((int)(EncoderVal),0,1023);

		if(this.adjustedRot.intMath(this.adjustedRot.intGet()+this.MoE)>EncIdeal.intGet()){
			this.hostCANTalon.set(motorSped);
		}else if(this.adjustedRot.intMath(this.adjustedRot.intGet()-this.MoE)<EncIdeal.intGet()){
			this.hostCANTalon.set(-motorSped);
		}else{
			this.hostCANTalon.set(0.0);
		}	
	}
	
	//Getters
	public CANTalon getCANTalon(){
		updateRot();
		return this.hostCANTalon;
	}
	public int getRot(){
		updateRot();
		return this.adjustedRot.intGet();
	}
	public int getRawRot(){
		updateRot();
		return this.actualRot;
	}
	//Updater
	private void updateRot(){
		this.actualRot = (int) this.hostCANTalon.getAnalogInRaw();
		this.adjustedRot.intSet(this.actualRot-this.offset);
	
	}
	
}
