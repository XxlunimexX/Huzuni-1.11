package net.halalaboos.mcwrapper.api.item.enchant;

import net.halalaboos.mcwrapper.api.attribute.Nameable;
import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Enchantment extends Nameable {

	int getMaxLevel();

	String name(int level);

	int getLevel(ItemStack stack);

}
