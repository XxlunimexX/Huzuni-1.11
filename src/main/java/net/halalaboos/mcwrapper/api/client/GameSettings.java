package net.halalaboos.mcwrapper.api.client;

public interface GameSettings {

	int getThirdPersonSetting();

	boolean getBobbing();

	void setBobbing(boolean bobbing);

	boolean isKeyDown(GameKeybind keybind);

	boolean isKeyPressed(GameKeybind keybind);

	void setKeyState(GameKeybind keybind, boolean state);

	int getKeyCode(String name);

	float getGamma();

	void setGamma(float gamma);
}
