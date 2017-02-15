package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.util.TextColor;

public class VClip extends BasicCommand {

	public VClip() {
		super(new String[] {"vc", "vclip", "up"}, "Teleports yourself up the specified amount of blocks.");
	}

	@Override
	public void giveHelp() {
		huzuni.addFormattedMessage("Usage: .vc %s<amount>%s", TextColor.DARK_GRAY, TextColor.GRAY);
	}

	@Override
	protected void runCommand(String input, String[] args) throws Exception {
		double amount = Double.parseDouble(args[0]);
		double x = mc.player.posX;
		double y = mc.player.posY + amount;
		double z = mc.player.posZ;
		mc.player.setPosition(x, y, z);
		huzuni.addFormattedMessage("Teleported %s %s block%s", amount > 0 ? "up" : "down", amount, amount == 1 ? "." : "s.");
	}
}
