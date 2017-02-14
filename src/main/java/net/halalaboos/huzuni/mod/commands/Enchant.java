package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.util.TextColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.translation.I18n;

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
		if (!mc.playerController.isInCreativeMode()) {
			huzuni.addChatMessage("You must be in creative mode to use this command!");
			return;
		}
		if (mc.player.inventory.getCurrentItem().isEmpty()) {
			huzuni.addChatMessage("You must be holding an item to enchant!");
			return;
		}
		if (args != null) {
			Enchantment enchantment  = getEnchantment(args[0]);
			if (enchantment != null) {
				int level = enchantment.getMaxLevel();
				if (args.length > 1) {
					level = Integer.parseInt(args[1]);
				}
				addEnchant(enchantment, (short) level, mc.player.inventory.getCurrentItem());
				huzuni.addFormattedMessage("Enchantment %s added!", enchantment.getTranslatedName(level));
				return;
			}
			huzuni.addFormattedMessage("Enchantment \"%s\" not found!", TextColor.GOLD + args[0] + TextColor.GRAY);
			return;
		}

		int enchantmentCount = 0;
		for (Enchantment enchantment : Enchantment.REGISTRY) {
			if (enchantment != null) {
				addEnchant(enchantment, (short)1000, mc.player.inventory.getCurrentItem());
				enchantmentCount++;
			}
		}
		huzuni.addFormattedMessage("Added %s enchants to your held item.", enchantmentCount);
	}

	private Enchantment getEnchantment(String name) {
		for (Enchantment enchantment : Enchantment.REGISTRY) {
			String translatedName = I18n.translateToLocal(enchantment.getName());
			if (translatedName.equalsIgnoreCase(name)
					|| translatedName.replaceAll(" ", "").equalsIgnoreCase(name)) {
				return enchantment;
			}
		}
		return null;
	}

	private void addEnchant(Enchantment ench, short level, ItemStack itemStack) {
		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		if (!itemStack.getTagCompound().hasKey("ench", 9)) {
			itemStack.getTagCompound().setTag("ench", new NBTTagList());
		}
		NBTTagList nbttaglist = itemStack.getTagCompound().getTagList("ench", 10);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setShort("id", (short)Enchantment.getEnchantmentID(ench));
		nbttagcompound.setShort("lvl", level);
		nbttaglist.appendTag(nbttagcompound);
		mc.playerController.updateController();
	}
}
