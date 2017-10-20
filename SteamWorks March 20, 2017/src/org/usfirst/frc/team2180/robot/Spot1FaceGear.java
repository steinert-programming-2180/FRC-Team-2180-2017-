package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Spot1FaceGear extends CommandGroup {
	
	public Spot1FaceGear() {
		addSequential(new TiltGearPickupSlightlyDown(1));
		addSequential(new DriveToBaseline(1));
		addSequential(new TurnToFacePeg(1));
	}
}
