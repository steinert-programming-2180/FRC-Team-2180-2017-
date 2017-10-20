package org.usfirst.frc.team2180.robot;


	
	import edu.wpi.first.wpilibj.Timer;
	import edu.wpi.first.wpilibj.command.Command;

	//do the precise calculations
	public class BaseLine extends Command {
		

		
		public BaseLine() {
			
		}
		
	    protected void initialize() {
	    	
	    }
		
		public void execute() {
			Timer timer = new Timer();
			timer.reset();
			timer.start();
			while (timer.get() < 1){
			Robot.right1.set(0.3);
			Robot.right2.set(0.3);
			Robot.right3.set(0.3);
			
			Robot.left1.set(-0.3);
			Robot.left2.set(-0.3);
			Robot.left3.set(-0.3);
		
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
