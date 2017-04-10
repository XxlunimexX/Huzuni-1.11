package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.mcwrapper.api.MCWrapper;

public final class Clear extends BasicCommand {

	public Clear() {
		super(new String[] { "clear", "clr" }, "Clears all messages from chat");
	}

	@Override
	protected void runCommand(String input, String[] args) {
		MCWrapper.getMinecraft().clearMessages(true);
	}

}
