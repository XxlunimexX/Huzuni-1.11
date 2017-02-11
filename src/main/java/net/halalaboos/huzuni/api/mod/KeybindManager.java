package net.halalaboos.huzuni.api.mod;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.event.KeyPressEvent;
import net.halalaboos.huzuni.api.event.EventManager.EventMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds keybinds and listens for key press events to invoke the keybinds.
 * */
public final class KeybindManager {
	
	private final List<Keybind> keybinds = new ArrayList<Keybind>();
	
	public KeybindManager() {
	}
	
	public void init() {
		Huzuni.INSTANCE.eventManager.addListener(this);
	}
	
	@EventMethod
	public void onKeyPress(KeyPressEvent event) {
		for (Keybind keybind : keybinds) {
			if (keybind.getKeycode() == event.keyCode)
				keybind.pressed();
		}
	}
	
	public void addKeybind(Keybind keybind) {
		this.keybinds.add(keybind);
	}
	
	public void removeKeybind(Keybind keybind) {
		this.keybinds.add(keybind);
	}
	
	public List<Keybind> getKeybinds() {
		return keybinds;
	}
}
