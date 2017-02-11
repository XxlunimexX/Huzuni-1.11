package net.halalaboos.huzuni.api.settings;

import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * A {@link Node} object that can be set enabled/disabled (true/false).
 * */
public class Toggleable extends Node {
		
	private boolean enabled;
	
	public Toggleable(String name, String description) {
		super(name, description);
	}
	
	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		json.addProperty(getName(), enabled);
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			enabled = json.get(getName()).getAsBoolean();
		}
	}
	
	/**
	 * Toggles the value of this {@link Node} object.
	 * */
	public void toggle() {
		setEnabled(!enabled);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
