package org.usfirst.frc2180.Guppy.subsystems;

import org.usfirst.frc2180.Guppy.RobotMap;
import org.usfirst.frc2180.Guppy.commands.Launcher;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	
	private final CANTalon shooterCANTalon = RobotMap.shooterCANTalon;

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new Launcher());
	}

}
