package net.halalaboos.tukio;

/**
 * Represents information of an event.
 *
 * TODO - Better description...
 */
public abstract class Event {

	/**
	 * Whether or not to cancel the event.
	 */
	private boolean cancelled = false;

	/**
	 * {@link #cancelled}
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * {@link #cancelled}
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
