package org.usfirst.frc.team2180.robot;

class Degree {
	
	private double Degrees = 0.0;
	
	public Degree(double number){
		Degrees=format(number);
	}
	public double get(){
		return Degrees;
	}
	
	public double add(double number){
		return format(Degrees+number);
	}
	
	public double multiply(double number){
		return format(Degrees*number);
	}
	public void set(double Degree){
		Degrees=format(Degree);
	}
	
	private double format(double number){
		boolean inFormat = false;
		while(!inFormat){
			if(number<1){
				inFormat=false;
				number = number+360;
			}else if(number>360){
				inFormat=false;
				number = number-360;
			}else{
				inFormat  = true;
			}
		}
		return number;
	}
	
}