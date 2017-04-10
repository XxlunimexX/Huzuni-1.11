package net.halalaboos.huzuni.api.mod.keybind;

import net.halalaboos.mcwrapper.api.event.input.KeyboardEvent;

import java.util.ArrayList;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.getEventManager;

/**
 * Holds keybinds and listens for key press events to invoke the keybinds.
 * */
public final class KeybindManager {
	
	private final List<Keybind> keybinds = new ArrayList<Keybind>();
	
	public KeybindManager() {
	}
	
	public void init() {
		getEventManager().subscribe(KeyboardEvent.class, event -> {
			for (Keybind keybind : keybinds) {
				if (keybind.getKeycode() == event.getKeyCode())
					keybind.pressed();
			}
		});
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
