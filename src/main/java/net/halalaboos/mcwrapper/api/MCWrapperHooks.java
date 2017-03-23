package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.event.input.KeyboardEvent;
import net.halalaboos.mcwrapper.api.event.input.MouseEvent;
import net.halalaboos.mcwrapper.api.event.world.WorldLoadEvent;
import net.halalaboos.mcwrapper.api.util.MouseButton;
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
}
