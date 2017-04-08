package net.halalaboos.mcwrapper.api.inventory;

import net.halalaboos.mcwrapper.api.item.ItemStack;

import java.util.Optional;

public interface Slot {

	int getIndex();

	int getSlotNumber();

	Optional<ItemStack> getItem();

}
