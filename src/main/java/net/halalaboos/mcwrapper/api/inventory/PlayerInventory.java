package net.halalaboos.mcwrapper.api.inventory;

import net.halalaboos.mcwrapper.api.item.ItemStack;

import java.util.Optional;

public interface PlayerInventory extends Inventory {

	Optional<ItemStack> getArmorStack(int slot);

	int getCurrentSlot();

	void setCurrentSlot(int slot);
}
