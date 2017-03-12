package net.halalaboos.mcwrapper.api.event.input;

import net.halalaboos.tukio.Event;

/**
 * Performed every time the user presses a key.
 *
 * <p>This event is only dispatched when in-game without any screens open (e.g. inventory, pause menu).</p>
 */
public class KeyboardEvent extends Event {

	/**
	 * The key code pressed.
	 */
	private final int keyCode;

	public KeyboardEvent(int keyCode) {
		this.keyCode = keyCode;
	}

	/**
	 * {@link #keyCode}
	 */
	public int getKeyCode() {
		return keyCode;
	}
}
