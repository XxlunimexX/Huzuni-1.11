package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.util.SystemUtils;

/**
 * @author brudin
 * @since 4/14/14
 */
public final class GetCoords extends BasicCommand {
	
	public GetCoords() {
		super(new String[] { "getcoords", "gc" }, "Copies your current coordinates to your clipboard.");
	}
	
	@Override
	protected void runCommand(String input, String[] args) {
		String coords = MCWrapper.getPlayer().getCoordinates();
		huzuni.addChatMessage(coords + " copied to your clipboard.");
		SystemUtils.copyToClipboard(coords);
	}
}
