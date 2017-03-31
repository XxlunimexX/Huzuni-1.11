package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.event.input.KeyboardEvent;
import net.halalaboos.mcwrapper.api.event.input.MouseEvent;
import net.halalaboos.mcwrapper.api.event.world.WorldLoadEvent;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;
import net.halalaboos.mcwrapper.api.world.World;

import static net.halalaboos.mcwrapper.api.MCWrapper.getEventManager;

public class MCWrapperHooks {

	public static void keyTyped(int keyCode) {
		getEventManager().publish(new KeyboardEvent(keyCode));
	}

	public static void mouseClicked(int mouseButton) {
		getEventManager().publish(new MouseEvent(MouseButton.getMouseButton(mouseButton)));
	}

	public static void joinWorld(World world) {
		getEventManager().publish(new WorldLoadEvent(world));
	}

	/**
	 * Whether or not to render player nameplates.
	 *
	 * While this may not look as pretty as dispatching an event for something like this, it doesn't
	 * really make much sense performance-wise to be dispatching an event every time a nameplate is rendered.  For
	 * larger servers with 50-100 players in a lobby, that would be 50-100 events being invoked every render tick.
	 * Calling a simple boolean like this for something as simple as turning on/off nameplate rendering, for now,
	 * is the safest way of handling this.
	 *
	 * The only use for this is for mods that overwrite vanilla nameplate rendering.
	 */
	public static boolean renderNames = true;
}
