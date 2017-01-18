package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class OI {
	
	Joystick stick = new Joystick(0);
	
	Button half = new JoystickButton(stick, 1),
		   sixty = new JoystickButton(stick, 2),
		   seventy = new JoystickButton(stick, 3),
		   eighty = new JoystickButton(stick, 4),
		   ninety = new JoystickButton(stick, 5);
	
	public OI(){
		half.whenPressed(new Half());
		sixty.whenPressed(new Sixty());
		seventy.whenPressed(new Seventy());
		eighty.whenPressed(new Eighty());
		ninety.whenPressed(new Ninety());
	}

}
