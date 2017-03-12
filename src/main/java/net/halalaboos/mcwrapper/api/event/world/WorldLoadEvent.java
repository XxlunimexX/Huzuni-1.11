package net.halalaboos.mcwrapper.api.event.world;

import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.tukio.Event;

public class WorldLoadEvent extends Event {

	private final World world;

	public WorldLoadEvent(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}
}
