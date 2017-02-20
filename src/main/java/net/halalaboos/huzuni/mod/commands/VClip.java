package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.util.TextColor;
import net.halalaboos.mcwrapper.api.util.Vector3d;

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
		ClientPlayer player = MCWrapper.getPlayer();
		double amount = Double.parseDouble(args[0]);
		double x = player.getX();
		double y = player.getY() + amount;
		double z = player.getZ();
		MCWrapper.getMinecraft().getPlayer().setLocation(new Vector3d(x, y, z));
		huzuni.addFormattedMessage("Teleported %s %s block%s", amount > 0 ? "up" : "down", amount, amount == 1 ? "." : "s.");
	}
}
