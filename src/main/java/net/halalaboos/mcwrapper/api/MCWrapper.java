package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.world.World;

public class MCWrapper {

	private static MinecraftAdapter adapter = null;

	public static void setAdapter(MinecraftAdapter adapter) {
		if (MCWrapper.adapter == null) MCWrapper.adapter = adapter;
	}

	public static MinecraftAdapter getAdapter() {
		return adapter;
	}

	public static String getMinecraftVersion() {
		return getAdapter().getMinecraftVersion();
	}

	public static void setWorld(World world) {
		getAdapter().setWorld(world);
	}

	public static World getWorld() {
		return getAdapter().getWorld();
	}
}
