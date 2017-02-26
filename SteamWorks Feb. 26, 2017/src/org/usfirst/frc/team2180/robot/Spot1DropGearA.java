package org.usfirst.frc.team2180.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Spot1DropGearA extends CommandGroup {
	
	public Spot1DropGearA() {
		addSequential(new GetToPeg(1));
		addSequential(new StartClient());
		addSequential(new StartServer());
		addSequential(new AlignGear());
		addSequential(new PlaceGear());
	}

}
