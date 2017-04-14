package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.client.Controller;
import net.halalaboos.mcwrapper.api.client.GameSettings;
import net.halalaboos.mcwrapper.api.client.Session;
import net.halalaboos.mcwrapper.api.client.gui.TextRenderer;
import net.halalaboos.mcwrapper.api.client.gui.screen.Screen;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.network.NetworkHandler;
import net.halalaboos.mcwrapper.api.network.ServerInfo;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.ResourcePath;
import net.halalaboos.mcwrapper.api.util.Resolution;
import net.halalaboos.mcwrapper.api.util.math.Result;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.Optional;

/**
 * This class contains many wrappers for most client-side related information provided in Minecraft's main class.
 *
 * <p>There are some exceptions here, where we will have getters/setters or wrappers for methods that are not in
 * the main Minecraft class but don't really require their own wrappers, such as {@link #loadRenderers()} or some
 * of the {@link ResourcePath} related methods. </p>
 */
public interface MinecraftClient {

	/**
	 * The right click delay timer is the delay between right clicking, so the lower the value, the lower the delay.
	 *
	 * <p>Placing blocks relies on this, so if this was set to 0, then you would place blocks very quickly.</p>
	 */
	int getRightClickDelayTimer();

	/**
	 * Sets the {@link #getRightClickDelayTimer()}
	 */
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
	 * 
	 * @param sentMessages Whether or not to clear the sent chat messages.
	 */
	void clearMessages(boolean sentMessages);

	boolean useUnicode();

	/**
	 * @return The current framerate.
	 */
	int getFPS();

	/**
	 * Returns the camera's position.  This is mainly used for render-related methods.
	 *
	 * @return The camera vector
	 */
	Vector3d getCamera();

	/**
	 * Returns the {@link TextRenderer}, or 'FontRenderer', which is used for rendering text in-game.
	 *
	 * @return The text renderer
	 * @see TextRenderer
	 */
	TextRenderer getTextRenderer();

	/**
	 * Returns the {@link NetworkHandler}, which is used for multi-player related things.
	 *
	 * @return The current network handler.
	 * @see NetworkHandler
	 */
	NetworkHandler getNetworkHandler();

	/**
	 * Returns the {@link InputStream} for the provided {@link ResourcePath}
	 *
	 * @param path The {@link ResourcePath}
	 * @return The input stream of the path.
	 */
	InputStream getInputStream(ResourcePath path) throws IOException;

	/**
	 * Shows the specified {@link Screen}.
	 * Don't use this.
	 * It's broken.
	 * Please.
	 *
	 * @param screen I SAID DON'T USE THIS
	 */
	void showScreen(Screen screen);

	/**
	 * Returns the {@code .minecraft} {@link File}.
	 *
	 * @return .minecraft
	 */
	File getSaveDirectory();

	/**
	 * If you have the HUD disabled by pressing F1 in-game, this will return true.
	 *
	 * @return If the HUD is enabled.
	 */
	boolean shouldShowGui();

	/**
	 * Returns the main 'view' entity.  In pretty much all cases, this will point to {@link #getPlayer()} (I think).
	 *
	 * @return The view entity.
	 */
	Entity getViewEntity();

	/**
	 * Prints the provided message to chat.
	 *
	 * @param message The message to print
	 */
	void printMessage(String message);

	/**
	 * Reloads the renderers.  This only is usable in-game, and is useful for updating the world renderer.
	 */
	void loadRenderers();

	/**
	 * Returns the {@link GameSettings}
	 *
	 * @return The client's game settings
	 * @see GameSettings
	 */
	GameSettings getSettings();

	Vector3i getMouseVector();

	Face getMouseFace();

	/**
	 * Returns the {@link Result mouse result}.  In most cases, this will never be {@link Optional#empty()}, but
	 * in the rare instance that it is, we are making it optional.
	 */
	Optional<Result> getMouseResult();

	/**
	 * Returns the current {@link Proxy}.
	 */
	Proxy getProxy();

	/**
	 * If you are moused over an {@link Entity}, this will return that Entity.
	 *
	 * @return The moused {@link Entity}
	 */
	Optional<Entity> getMousedEntity();

	/**
	 * Returns the player's view vector.
	 */
	Vector3d getPlayerView();

	/**
	 * Returns whether or not the {@link #getScreen()} is null.
	 *
	 * @return If there is a Gui open
	 */
	boolean isScreenOpen();

	/**
	 * Returns the current GuiScreen.  Since we don't have a GuiScreen interface/wrapper, this just returns an
	 * object for now; you can still use this to check if the screen is an instance of another screen, such as
	 * {@link net.halalaboos.mcwrapper.api.inventory.gui.ChestGui}.
	 *
	 * @return The current (Gui) screen.
	 */
	Object getScreen();

	/**
	 * Binds the texture in the specified {@link ResourcePath} using Minecraft's texture manager.
	 * @param path The path of the texture.
	 */
	void bindTexture(ResourcePath path);

	Session session();

	boolean isHurtcamEnabled();

	void setHurtcamEnabled(boolean enabled);

	boolean isOverlayEnabled();

	void setOverlayEnabled(boolean enabled);

	boolean isWeatherEnabled();

	void setWeatherEnabled(boolean enabled);
}
