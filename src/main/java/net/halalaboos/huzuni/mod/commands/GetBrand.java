package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public final class GetBrand extends BasicCommand {
	
	public GetBrand() {
		super(new String[] { "getbrand", "brand" }, "Gives server brand info.");
	}

	@Override
	protected void runCommand(String input, String[] args) {
		if (!getMinecraft().isRemote()) {
			huzuni.addChatMessage("You're not connected to a server!");
		} else {
			huzuni.addChatMessage("Brand: " + getPlayer().getBrand());
		}
	}
}
