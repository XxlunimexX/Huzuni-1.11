package net.halalaboos.mcwrapper.api.client;

/**
 * Represents the client's game settings.
 *
 * Minecraft handles most of the settings in a fairly messy way, so making individual getters/setters for each and
 * every game setting seemed like a waste of time.  For now, we only have wrappers for a few settings that most mods
 * need.
 *
 * TODO: Documentation
 */
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
