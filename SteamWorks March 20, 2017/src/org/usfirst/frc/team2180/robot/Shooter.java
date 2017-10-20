package org.usfirst.frc.team2180.robot;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Shooter extends PIDSubsystem {
	CANTalon shooterMotor2 = Robot.shooterMotor2;
	
	public Shooter() {
		super(1.0, 0.0, 0.0, 0.0);
		getPIDController().setContinuous(true);
		LiveWindow.addActuator("Shooter", "PIDSubsystem Controller", getPIDController());
	}

	@Override
	protected double returnPIDInput() {
		return shooterMotor2.getPulseWidthVelocity() & 0xFFF;
	}

	@Override
	protected void usePIDOutput(double output) {
		shooterMotor2.pidWrite(output);
	}

	@Override
	protected void initDefaultCommand() {
		
	}
}
