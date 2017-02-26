package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Spot3DropGearC extends CommandGroup {
	
	public Spot3DropGearC() {
		addSequential(new GetToPeg(3));
		addSequential(new StartClient());
		addSequential(new StartServer());
		addSequential(new AlignGear());
		addSequential(new PlaceGear());
	}

}