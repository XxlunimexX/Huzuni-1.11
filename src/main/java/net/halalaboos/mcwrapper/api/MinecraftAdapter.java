package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.util.Resolution;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;

/**
 * Represents the Minecraft client
 */
public interface MinecraftAdapter {

	ClientPlayer getPlayer();

	World getWorld();
	void setWorld(World world);

	Resolution getScreenResolution();

	String getMinecraftVersion();

}
