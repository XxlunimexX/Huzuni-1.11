package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.Tupac;

public final class Clear extends BasicCommand {

	public Clear() {
		super(new String[] { "clear", "clr" }, "Clears all messages from chat");
	}

	@Override
	protected void runCommand(String input, String[] args) {
		Tupac.getMinecraft().clearMessages(true);
	}

}
