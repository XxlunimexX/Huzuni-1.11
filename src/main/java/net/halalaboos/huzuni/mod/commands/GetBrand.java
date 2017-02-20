package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.MCWrapper;

public final class GetBrand extends BasicCommand {
	
	public GetBrand() {
		super(new String[] { "getbrand", "brand" }, "Gives server brand info.");
		
	}

	@Override
	protected void runCommand(String input, String[] args) {
		if (!MCWrapper.getMinecraft().isRemote()) {
			huzuni.addChatMessage("You're not connected to a server!");
		} else {
			huzuni.addChatMessage("Brand: " + MCWrapper.getPlayer().getBrand());
		}
	}
}
