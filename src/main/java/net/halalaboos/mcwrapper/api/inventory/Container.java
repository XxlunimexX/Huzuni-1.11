package net.halalaboos.mcwrapper.api.inventory;

import net.halalaboos.mcwrapper.api.item.ItemStack;

import java.util.Optional;

public interface Container {

	Slot getSlotAt(int index);

	default Optional<ItemStack> getStack(int index) {
		return getSlotAt(index).getItem();
	}

	int getId();

}
