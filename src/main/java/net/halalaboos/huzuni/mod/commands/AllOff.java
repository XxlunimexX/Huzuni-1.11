package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.huzuni.api.mod.Mod;

public final class AllOff extends BasicCommand {
	
    public AllOff() {
		super(new String[] { "alloff", "off" }, "Turns all enabled mods off.");
	}
    
	@Override
	protected void runCommand(String input, String[] args) {
        huzuni.modManager.getMods().stream().filter(Mod::isEnabled).forEach(Mod::toggle);
        huzuni.addChatMessage("All mods turned off.");
	}
}