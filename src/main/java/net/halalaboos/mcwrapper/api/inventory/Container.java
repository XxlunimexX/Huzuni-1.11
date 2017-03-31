package net.halalaboos.mcwrapper.api.inventory;

import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Container {

	Slot getSlotAt(int index);

	default ItemStack getStack(int index) {
		return getSlotAt(index).getItem();
	}

	int getId();
}
