package net.halalaboos.mcwrapper.api.event;

import net.halalaboos.mcwrapper.api.util.MouseButton;
import net.halalaboos.tukio.Event;

public class MouseEvent extends Event {

	private final MouseButton mouseButton;

	public MouseEvent(MouseButton mouseButton) {
		this.mouseButton = mouseButton;
	}

	public MouseButton getButton() {
		return mouseButton;
	}
}
