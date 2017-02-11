package net.halalaboos.huzuni.api.mod;

import com.google.gson.JsonObject;
import net.halalaboos.huzuni.api.settings.Node;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

/**
 * Basic implementation of the keybind interface.
 * */
public abstract class BasicKeybind extends Node implements Keybind {
	
	private int keyCode = -1;
	
	public BasicKeybind(String name, String description, int keyCode) {
		super(name, description);
		this.keyCode = keyCode;
	}

	@Override
	public void setKeycode(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public int getKeycode() {
		return keyCode;
	}

	@Override
	public boolean isBound() {
		return keyCode > 0;
	}

	@Override
	public String getKeyName() {
		return isBound() ? Keyboard.getKeyName(keyCode) : "None";
	}

	@Override
	public boolean isPressed() {
		return Keyboard.isKeyDown(keyCode);
	}

	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		json.addProperty(getName(), keyCode);
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json))
		keyCode = json.get(getName()).getAsInt();
	}
	
}
