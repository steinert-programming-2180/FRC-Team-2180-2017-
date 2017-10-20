package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Pos2AutonGear extends CommandGroup {
	
	public Pos2AutonGear() {
		//addSequential(new TiltGearPickupSlightlyDown(1));
		addSequential(new DriveToBaseline(2));
		//addSequential(new TiltGearPickupSlightlyDown(2));
		//addSequential(new BackAwayFromPeg());
	}
}
