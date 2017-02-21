package org.usfirst.frc.team2180.robot;

import java.io.IOException;
import java.net.Socket;

import edu.wpi.first.wpilibj.command.Command;

public class StartClient extends Command {
	
	public StartClient() {

	}
	
	protected void initialize(){
		
		
	}
	
	protected void execute(){
		Socket s = null;
		try {
			s = new Socket("raspberrypi2", 5000);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
}
