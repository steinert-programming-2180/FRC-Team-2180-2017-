package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Alliance1Peg1 extends CommandGroup {
	
	public Alliance1Peg1() {
		//addSequential(new TiltGearPickupSlightlyDown(1));
		addSequential(new DriveToBaseline(1));
		addSequential(new TurnToFacePeg(1));
		addSequential(new ApproachPeg());
		//addSequential(new TiltGearPickupSlightlyDown(2));
		//addSequential(new BackAwayFromPeg());
	}
}
