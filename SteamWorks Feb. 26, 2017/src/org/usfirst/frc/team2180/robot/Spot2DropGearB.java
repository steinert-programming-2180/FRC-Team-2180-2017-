package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Spot2DropGearB extends CommandGroup {
	
	public Spot2DropGearB() {
		addSequential(new GetToPeg(2));
		addSequential(new StartClient());
		addSequential(new StartServer());
		addSequential(new AlignGear());
		addSequential(new PlaceGear());
	}

}
