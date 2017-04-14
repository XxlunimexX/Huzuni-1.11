package net.halalaboos.huzuni.api.mod;

import net.halalaboos.huzuni.api.mod.keybind.BasicKeybind;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.MinecraftClient;

/**
 * Mod which contains a basic keybind that toggles it.
 * */
public class BasicMod extends Mod {

	protected MinecraftClient mc = MCWrapper.getMinecraft();

	protected BasicKeybind keybind;
	
	public BasicMod(String name, String description) {
		this(name, description, -1);
	}
	
	public BasicMod(String name, String description, int keyCode) {
		super(name, description);
		setAuthor("Halalaboos");
		keybind = new BasicKeybind("Keybind", "Keybind for " + name, keyCode) {
			@Override
			public void pressed() {
				toggle();
			}
		};
		settings.addChildren(keybind);
		this.settings.setDisplayable(true);
	}

	public BasicKeybind getKeybind() {
		return keybind;
	}

	public void setKeybind(BasicKeybind keybind) {
		this.keybind = keybind;
	}
	
}
