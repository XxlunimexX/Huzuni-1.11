package net.halalaboos.mcwrapper.impl.mixin.client;

import net.halalaboos.mcwrapper.api.client.GameKeybind;
import net.halalaboos.mcwrapper.api.client.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.settings.GameSettings.class)
public class MixinGameSettings implements GameSettings {
	@Shadow public int thirdPersonView;

	@Shadow public boolean viewBobbing;

	@Shadow public KeyBinding[] keyBindings;

	@Shadow public float gammaSetting;

	@Override
	public int getThirdPersonSetting() {
		return thirdPersonView;
	}

	@Override
	public boolean getBobbing() {
		return viewBobbing;
	}

	@Override
	public void setBobbing(boolean bobbing) {
		this.viewBobbing = bobbing;
	}

	@Override
	public boolean isKeyDown(GameKeybind keybind) {
		int keyCode = keybind.getKeyCode();
		return (keyCode != 0 && keyCode < 256) && (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) :
				Keyboard.isKeyDown(keyCode));
	}

	@Override
	public boolean isKeyPressed(GameKeybind keybind) {
		return get(keybind) != null && get(keybind).isPressed();
	}

	@Override
	public void setKeyState(GameKeybind keybind, boolean state) {
		KeyBinding.setKeyBindState(keybind.getKeyCode(), state);
	}

	private KeyBinding get(GameKeybind keybind) {
		for (KeyBinding keyBinding : this.keyBindings) {
			if (keyBinding.getKeyDescription().equals(keybind.name)) {
				return keyBinding;
			}
		}
		return null;
	}

	@Override
	public int getKeyCode(String name) {
		for (KeyBinding keyBinding : this.keyBindings) {
			if (keyBinding.getKeyDescription().equals(name)) {
				return keyBinding.getKeyCode();
			}
		}
		return -1;
	}

	@Override
	public float getGamma() {
		return gammaSetting;
	}

	@Override
	public void setGamma(float gamma) {
		this.gammaSetting = gamma;
	}
}
