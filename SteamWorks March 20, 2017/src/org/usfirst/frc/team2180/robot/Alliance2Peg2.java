package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Alliance2Peg2 extends Command{
	
	Timer timer;
	double seek1 = 0;
	double seek2 = 0;
	
	
	public Alliance2Peg2(){
		timer = new Timer();
		seek1 = (2163);
		seek2 = (1500);
	}
	
	public void initialize() {
		timer.start();
	}
	
	public void execute() {
		if (timer.get() < 2){
			
			Robot.left1.set(-0.3);
			Robot.left2.set(-0.3);
			Robot.left3.set(-0.3);
			
			Robot.right1.set(0.3);
			Robot.right2.set(0.3);
			Robot.right3.set(0.3);
			
		}else{
			Robot.left1.set(0.0);
			Robot.left2.set(0.0);
			Robot.left3.set(0.0);
			
			Robot.right1.set(0.0);
			Robot.right2.set(0.0);
			Robot.right3.set(0.0);
		}
		
		if (timer.get() > 4 && timer.get() <= 5){
			if (seek1 > 60){
				Robot.gearPickUp.set(Math.signum(seek1)*(0.2));
			
			}else{
				Robot.gearPickUp.set(0.0);
		}		
	}
		
		if (timer.get() > 5 && timer.get() <=6){
			if (seek2 > 5){
				Robot.gearPickUp.set(Math.signum(seek2)*(0.2));
				
				Robot.left1.set(-0.2);
				Robot.left2.set(-0.2);
				Robot.left3.set(-0.2);
				
				Robot.right1.set(0.2);
				Robot.right2.set(0.2);
				Robot.right3.set(0.2);
			}
		}else{
			Robot.gearPickUp.set(0.0);
			
			Robot.left1.set(0.0);
			Robot.left2.set(0.0);
			Robot.left3.set(0.0);
			
			Robot.right1.set(0.0);
			Robot.right2.set(0.0);
			Robot.right3.set(0.0);
		}
}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
	
}
