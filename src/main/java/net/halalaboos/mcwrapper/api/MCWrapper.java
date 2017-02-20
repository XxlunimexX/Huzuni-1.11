package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.world.World;

/**
 * The main Tupac class, serves as a wrapper for the current MinecraftAdapter.
 */
public class MCWrapper {

	private static MinecraftAdapter adapter = null;

	public static void setAdapter(MinecraftAdapter adapter) {
		if (MCWrapper.adapter == null) MCWrapper.adapter = adapter;
	}

	public static MinecraftAdapter getAdapter() {
		return adapter;
	}

	public static MinecraftClient getMinecraft() {
		return getAdapter().getMinecraft();
	}

	public static ClientPlayer getPlayer() {
		return getMinecraft().getPlayer();
	}

	public static String getMinecraftVersion() {
		return getAdapter().getMinecraftVersion();
	}

	public static void onSetWorld(World world) {
		getAdapter().setWorld(world);
	}

	public static World getWorld() {
		return getMinecraft().getWorld();
	}
}
