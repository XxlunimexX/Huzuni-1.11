package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.huzuni.api.mod.command.Command;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;

/**
 * @author brudin
 * @since 5/17/14
 */
public final class Commands extends BasicCommand {
	
	public Commands() {
		super(new String[]{ "commands", "cmds" }, "Lists all loaded commands.");
	}

	@Override
	protected void runCommand(String input, String[] args) {
		huzuni.addChatMessage(TextColor.GOLD + "--- " + TextColor.GRAY + "Type .help or .help [command] for help." + TextColor.GOLD + " ---");
		huzuni.addChatMessage(getAllCommands());
	}

	private String getAllCommands() {
		String out = "";
		for(Command command : huzuni.commandManager.getCommands()) {
			out += command.getAliases()[0] + ", ";
		}
		return out.substring(0, out.length() - 2);
	}
}

