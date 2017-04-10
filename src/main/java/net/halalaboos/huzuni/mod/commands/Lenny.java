package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.mcwrapper.api.util.SystemUtils;

public final class Lenny extends BasicCommand {

	public Lenny() {
		super(new String[] { "lenny", "len" }, "( ͡° ͜ʖ ͡°)");
	}
	
	@Override
	protected void runCommand(String input, String[] args) {
		SystemUtils.copyToClipboard("( ͡° ͜ʖ ͡°)");
		huzuni.addChatMessage("Copied to clipboard.");
	}

}
