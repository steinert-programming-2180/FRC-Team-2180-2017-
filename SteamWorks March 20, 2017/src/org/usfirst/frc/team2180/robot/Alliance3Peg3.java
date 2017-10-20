package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Alliance3Peg3 extends CommandGroup {
	
	public Alliance3Peg3() {
		//addSequential(new TiltGearPickupSlightlyDown(1));
		addSequential(new DriveToBaseline(3));
		addSequential(new TurnToFacePeg(3));
		addSequential(new ApproachPeg());
		//addSequential(new TiltGearPickupSlightlyDown(2));
		//addSequential(new BackAwayFromPeg());
	}
}
