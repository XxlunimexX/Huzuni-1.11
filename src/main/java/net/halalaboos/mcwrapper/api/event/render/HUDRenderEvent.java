package net.halalaboos.mcwrapper.api.event.render;

import net.halalaboos.tukio.Event;

public class HUDRenderEvent extends Event {

	private boolean debugEnabled; //temporary, todo

	private float delta;

	public HUDRenderEvent(boolean debugEnabled, float delta) {
		this.debugEnabled = debugEnabled;
		this.delta = delta;
	}

	public float getDelta() {
		return delta;
	}

	public boolean isDebugEnabled() {
		return debugEnabled;
	}
}
