package net.halalaboos.mcwrapper.api.entity;

import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface ItemPickup extends Entity {

	/**
	 * @return The amount of time (in ticks) before the Item can be picked up.
	 */
	int getPickupDelay();

	/**
	 * @return The item that will be picked up.
	 */
	ItemStack getItem();
}
