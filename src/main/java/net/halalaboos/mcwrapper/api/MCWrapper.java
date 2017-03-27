package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.client.Controller;
import net.halalaboos.mcwrapper.api.client.GLState;
import net.halalaboos.mcwrapper.api.client.GameSettings;
import net.halalaboos.mcwrapper.api.client.gui.TextRenderer;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.tukio.EventManager;

/**
 * Serves as a wrapper for the current {@link MinecraftAdapter}.  This is what any code using MCWrapper should point to,
 * rather than the version-specific adapter.
 */
public class MCWrapper {

	private static MinecraftAdapter adapter = null;
	private static EventManager eventManager = null;

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

	public static World getWorld() {
		return getMinecraft().getWorld();
	}

	public static Controller getController() {
		return getMinecraft().getController();
	}

	public static TextRenderer getTextRenderer() {
		return getMinecraft().getTextRenderer();
	}

	public static GLState getGLStateManager() {
		return getAdapter().getGLStateManager();
	}

	public static GameSettings getSettings() {
		return getMinecraft().getSettings();
	}

	public static EventManager getEventManager() {
		if (eventManager == null) {
			eventManager = new EventManager();
		}
		return eventManager;
	}
}
