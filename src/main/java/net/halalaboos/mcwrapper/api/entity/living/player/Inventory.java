package net.halalaboos.mcwrapper.api.entity.living.player;

import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Inventory {

	/**
	 * @return The {@link ItemStack} in the specified slot.
	 */
	ItemStack getStack(int slot);

	/**
	 * @return The size of the inventory.
	 */
	int getSize();

}
