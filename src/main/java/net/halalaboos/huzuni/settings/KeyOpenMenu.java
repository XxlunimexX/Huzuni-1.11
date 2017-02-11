package net.halalaboos.huzuni.settings;


import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.mod.BasicKeybind;
import net.halalaboos.huzuni.gui.screen.HuzuniSettingsMenu;
import net.minecraft.client.Minecraft;
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
		Minecraft.getMinecraft().displayGuiScreen(new HuzuniSettingsMenu(Huzuni.INSTANCE.guiManager.widgetManager, Huzuni.INSTANCE.guiManager.settingsMenu));
	}

}
