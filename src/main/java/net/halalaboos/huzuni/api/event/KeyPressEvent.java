package net.halalaboos.huzuni.api.event;

/**
 * This event is fired when a key is pressed by the player.
 * */
public final class KeyPressEvent {

	/**
	 * The key code which represents the key pressed by the player.
	 * */
	public final int keyCode;
	
	public KeyPressEvent(int keyCode) {
		this.keyCode = keyCode;
	}
	
}
