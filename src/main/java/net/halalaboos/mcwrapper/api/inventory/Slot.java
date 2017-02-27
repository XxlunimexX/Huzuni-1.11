package net.halalaboos.mcwrapper.api.inventory;

import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Slot {

	int getIndex();

	ItemStack getItem();

	boolean hasItem();

}
