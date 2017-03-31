package net.halalaboos.mcwrapper.api.event.input;

import net.halalaboos.mcwrapper.api.util.enums.MouseButton;
import net.halalaboos.tukio.Event;

/**
 * Performed every time the user clicks the mouse.
 *
 * <p>This event is only dispatched when in-game without any screens open (e.g. inventory, pause menu).</p>
 */
public class MouseEvent extends Event {

	/**
	 * The mouse button pressed.
	 */
	private final MouseButton mouseButton;

	public MouseEvent(MouseButton mouseButton) {
		this.mouseButton = mouseButton;
	}

	/**
	 * {@link #mouseButton}
	 */
	public MouseButton getButton() {
		return mouseButton;
	}
}
