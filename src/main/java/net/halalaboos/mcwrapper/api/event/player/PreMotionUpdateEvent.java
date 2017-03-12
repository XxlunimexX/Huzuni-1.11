package net.halalaboos.mcwrapper.api.event.player;

import net.halalaboos.tukio.Cancellable;
import net.halalaboos.tukio.Event;

public class PreMotionUpdateEvent extends Event implements Cancellable {

	private boolean cancelled = false;

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
