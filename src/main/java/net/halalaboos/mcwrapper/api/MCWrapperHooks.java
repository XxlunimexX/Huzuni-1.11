package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.event.KeyboardEvent;
import net.halalaboos.mcwrapper.api.event.MouseEvent;
import net.halalaboos.mcwrapper.api.util.MouseButton;

import static net.halalaboos.mcwrapper.api.MCWrapper.getEventManager;

public class MCWrapperHooks {

	public static void keyTyped(int keyCode) {
		getEventManager().publish(new KeyboardEvent(keyCode));
	}

	public static void mouseClicked(int mouseButton) {
		getEventManager().publish(new MouseEvent(MouseButton.getMouseButton(mouseButton)));
	}

}
