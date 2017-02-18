package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.world.World;

/**
 * Represents the Minecraft client
 */
public interface MinecraftAdapter {

	String getMinecraftVersion();

	void setWorld(World world);

	MinecraftClient getMinecraft();

}
