package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Spot3FaceGear extends CommandGroup {
	
	public Spot3FaceGear() {
		addSequential(new TiltGearPickupSlightlyDown(1));
		addSequential(new DriveToBaseline(3));
		addSequential(new TurnToFacePeg(3));
	}
}
