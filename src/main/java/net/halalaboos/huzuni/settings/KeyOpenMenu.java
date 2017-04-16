package net.halalaboos.huzuni.settings;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.mod.keybind.BasicKeybind;
import net.halalaboos.huzuni.gui.screen.HuzuniSettingsMenu;
import net.halalaboos.mcwrapper.api.MCWrapper;
import org.lwjgl.input.Keyboard;

/**
 * The keybind used to open the settings menu.
 * */
public class KeyOpenMenu extends BasicKeybind {
	
	public KeyOpenMenu() {
		super("Menu Key", "Keybind which opens the menu screen", Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void pressed() {
		MCWrapper.getMinecraft().showScreen(new HuzuniSettingsMenu(Huzuni.INSTANCE.guiManager.widgetManager, Huzuni.INSTANCE.guiManager.settingsMenu));
	}

}
