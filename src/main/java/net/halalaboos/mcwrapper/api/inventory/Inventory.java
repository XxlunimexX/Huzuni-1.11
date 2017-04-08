package net.halalaboos.mcwrapper.api.inventory;

import net.halalaboos.mcwrapper.api.item.ItemStack;

import java.util.Optional;

public interface Inventory {

	int getSize();

	Optional<ItemStack> getStack(int slot);
}
