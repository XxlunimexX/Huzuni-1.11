package net.halalaboos.mcwrapper.api.item;

import net.halalaboos.mcwrapper.api.attribute.Nameable;

public interface Enchantment extends Nameable {

	int getMaxLevel();

	String name(int level);

}
