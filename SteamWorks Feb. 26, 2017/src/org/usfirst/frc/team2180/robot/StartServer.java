package org.usfirst.frc.team2180.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StartServer extends Command {
	AnalogInput ultrasonicAI = Robot.ultrasonicAI;
	
	public StartServer() {

	}
	
	protected void initialize(){
		
		
	}
	
	protected void execute() {
		SmartDashboard.putString("Server Status", "Starting...");
		int clientNumber = 1;
		ServerSocket listener = null;
		try {
			listener = new ServerSocket(5000);
		} catch (IOException e) {
			SmartDashboard.putString("Server Status", "ERROR");
		}
		
		SmartDashboard.putString("Server Status", "Server socket created");
		SmartDashboard.putString("Server Status", "Waiting...");
		
		int count = 499;
		
		try {
			SmartDashboard.putString("Server Status", "Waiting...");
			SmartDashboard.putNumber("Debug", ++count);
			new GetCameraData(listener.accept(), clientNumber++, ultrasonicAI).start();
			SmartDashboard.putString("Server Status", "Thread #" + clientNumber);
		} catch (IOException e) {
			SmartDashboard.putString("Server Status", "ERROR");
		} finally {
			SmartDashboard.putString("Server Status", "Inside the finally block");
			try {
				listener.close();
			} catch (IOException e) {
				SmartDashboard.putString("Server Status", "ERROR");
			}
		}
	}
	
	protected boolean isFinished(){
		return true; //check this while testing
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
}

class GetCameraData extends Thread {
	private Socket socket;
	private int clientNumber;
	AnalogInput ultrasonicAI;
	
	int width = 0;
	double volt0 = 0, angle = 0, distanceToMove = 0, dis = 0, radius = 0, angleRad = 0;
	String fs0;
	
	public GetCameraData(Socket socket, int clientNumber, AnalogInput ultrasonicAI) {
		this.socket = socket;
		this.clientNumber = clientNumber;
		this.ultrasonicAI = ultrasonicAI;
		SmartDashboard.putString("Server Status", "New connection with client #" + clientNumber + " at " + socket);
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println("Hello, you are client #" + clientNumber + ".");
			
			SmartDashboard.putString("Server Status", "Message read successfully");
			
			width = Integer.parseInt(in.readLine());			
			
			volt0 = ultrasonicAI.getAverageVoltage();
			dis = volt0/(5.0/1024.0)*0.0328084*12;
			angle = Math.asin(4.125/dis) * 180/Math.PI;
			angleRad = Math.asin(4.125/dis);
			distanceToMove = dis * Math.sin(angleRad);
			radius = Math.sqrt((dis*dis)-(distanceToMove*distanceToMove));
			
			SmartDashboard.putNumber("Width of reflective tape", width);
			SmartDashboard.putString("Client Message", width + " pixels");
			SmartDashboard.putNumber("Ultra dis", dis);
			SmartDashboard.putNumber("Distance to move", distanceToMove);
			SmartDashboard.putNumber("Angle num", angle);
			SmartDashboard.putNumber("Pixels per inch", width/1.375);
			SmartDashboard.putNumber("Radius", radius);
			SmartDashboard.putNumber("Arc length to traverse", radius * angleRad);
			
		} catch (IOException e) {
			SmartDashboard.putString("Server Status", "ERROR");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				SmartDashboard.putString("Server Status", "ERROR");
			}
			
			SmartDashboard.putString("Server Status", "Connection with client #" + clientNumber + " closed");
		}
	}
}

