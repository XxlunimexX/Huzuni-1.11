package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.Tupac;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author brudin
 * @version 1.0
 * @since 4/14/14
 */
public final class GetCoords extends BasicCommand {
	
	public GetCoords() {
		super(new String[] { "getcoords", "gc" }, "Copies your current coordinates to your clipboard.");
		
	}
	
	@Override
	protected void runCommand(String input, String[] args) {
		String coords = Tupac.getPlayer().getCoordinates();
		huzuni.addChatMessage(coords + " copied to your clipboard.");
		GuiScreen.setClipboardString(coords);
	}
}
