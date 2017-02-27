package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.client.Controller;
import net.halalaboos.mcwrapper.api.network.ServerInfo;
import net.halalaboos.mcwrapper.api.util.Resolution;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.world.World;

import java.util.Optional;

/**
 * Represents the Minecraft client.
 */
public interface MinecraftClient {

	/**
	 * The right click delay timer is the delay between right clicking, so the lower the value, the lower the delay.
	 * <p>Placing blocks relies on this, so if this was set to 0, then you would place blocks very quickly.</p>
	 */
	int getRightClickDelayTimer();

	void setRightClickDelayTimer(int rightClickDelayTimer);

	float getTimerSpeed();

	void setTimerSpeed(float timerSpeed);

	/**
	 * @return The render partial ticks of the game, useful for interpolation in render-related methods.
	 */
	float getDelta();

	/**
	 * @return The player
	 */
	ClientPlayer getPlayer();

	/**
	 * @return The player controller
	 */
	Controller getController();

	/**
	 * @return The current world.
	 */
	World getWorld();

	/**
	 * @return The resolution of the screen.  Includes the raw resolution and the scaled resolution.
	 */
	Resolution getScreenResolution();

	/**
	 * @return Whether or not the game is singleplayer or multiplayer.
	 */
	boolean isRemote();

	/**
	 * @return The current server info.
	 */
	Optional<ServerInfo> getServerInfo();

	/**
	 * Clears the chat.  This doesn't work currently in Huzuni due to the chat gui being different.
	 * @param sentMessages Whether or not to clear the sent chat messages.
	 */
	void clearMessages(boolean sentMessages);

	/**
	 * @return The current framerate.
	 */
	int getFPS();

	Vector3d getCamera();
}
