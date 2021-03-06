package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.huzuni.api.mod.keybind.BasicKeybind;
import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;
import org.lwjgl.input.Keyboard;

public final class Bind extends BasicCommand {

	public Bind() {
		super(new String[] { "bind", "b" }, "Set the keybind for mods");
	}
	
	@Override
	public void giveHelp() {
		huzuni.addChatMessage(".bind \"mod\" \"key\"");
	}

	@Override
	protected void runCommand(String input, String[] args) {
		String modName = args[0];
		int keyCode = Keyboard.getKeyIndex(args[1].toUpperCase());
		Mod mod = huzuni.modManager.getModIgnoreCase(modName);
		if (mod != null) {
			BasicKeybind keybind = huzuni.modManager.getKeybind(mod);
			if (keybind != null) {
				keybind.setKeycode(keyCode);
				String message = String.format("%s is now bound to %s!", mod.getName(), TextColor.GREEN + Keyboard.getKeyName(keyCode) + TextColor.RESET);
				huzuni.addNotification(NotificationType.INFO, "Bind Command", 5000, message);
			} else {
				huzuni.addNotification(NotificationType.ERROR, "Bind Command", 5000, String.format("%s is not a keybindable mod!", mod.getName()));
			}
		} else {
			huzuni.addNotification(NotificationType.ERROR, "Bind Command", 5000, String.format("%s is not a mod!", modName));
		}
	}

}
