package net.halalaboos.mcwrapper.api.client;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

public enum GameKeybind {

	ATTACK("key.attack"),
	SNEAK("key.sneak"),
	JUMP("key.jump");

	public String name;

	GameKeybind(String name) {
		this.name = name;
	}

	public int getKeyCode() {
		return getMinecraft().getSettings().getKeyCode(name);
	}
}
