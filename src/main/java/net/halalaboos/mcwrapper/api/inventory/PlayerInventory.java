package net.halalaboos.mcwrapper.api.inventory;

import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface PlayerInventory {

	ItemStack getStack(int slot);

}
