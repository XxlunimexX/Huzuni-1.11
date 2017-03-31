package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.Enchantment;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;

import static net.halalaboos.mcwrapper.api.MCWrapper.getAdapter;
import static net.halalaboos.mcwrapper.api.MCWrapper.getController;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.entity.living.player.GameType.CREATIVE;

public class Enchant extends BasicCommand {

	public Enchant() {
		super(new String[] { "enchant", "enc" }, "Enchants your held item with the specified enchants.");
	}

	@Override
	public void giveHelp() {
		huzuni.addFormattedMessage("Basic usage: .enc %s %s",
				TextColor.GRAY + "enchantment name" + TextColor.RESET,
				TextColor.GRAY + "level");
		huzuni.addFormattedMessage("Using just %s will enchant the item with all enchantments at their max level.",
				TextColor.GRAY + ".enc" + TextColor.RESET);
	}

	@Override
	protected void runCommand(String input, String[] args) throws Exception {
		if (getController().getGameType() != CREATIVE) {
			huzuni.addChatMessage("You must be in creative mode to use this command!");
			return;
		}
		if (getPlayer().getHeldItem(Hand.MAIN) == null) {
			huzuni.addChatMessage("You must be holding an item to enchant!");
			return;
		}
		if (args != null) {
			getPlayer().getHeldItem(Hand.MAIN).addEnchant(args[0], ((short) Integer.parseInt(args[1])));
			huzuni.addChatMessage("Enchantment added!");
			return;
		}

		int enchantmentCount = 0;
		for (Enchantment enchantment : getAdapter().getEnchantmentRegistry().getEnchants()) {
			if (enchantment != null) {
				getPlayer().getHeldItem(Hand.MAIN).addEnchant(enchantment.name(), (short)1000);
				enchantmentCount++;
			}
		}
		huzuni.addFormattedMessage("Added %s enchants to your held item.", enchantmentCount);
	}
}
