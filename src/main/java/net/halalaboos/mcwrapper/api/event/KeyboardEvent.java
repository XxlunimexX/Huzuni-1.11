package net.halalaboos.mcwrapper.api.event;

import net.halalaboos.tukio.Event;

public class KeyboardEvent extends Event {

	private final int keyCode;

	public KeyboardEvent(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}
}
