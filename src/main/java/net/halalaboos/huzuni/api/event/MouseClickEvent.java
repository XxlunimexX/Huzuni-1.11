package net.halalaboos.huzuni.api.event;

/**
 * This event is fired when the player clicks with their mouse.
 * */
public final class MouseClickEvent {
	
	/**
	 * The ID representing the button clicked.
	 * */
	public final int buttonId;
	
	public MouseClickEvent(int buttonId) {
		this.buttonId = buttonId;
	}
	
}
