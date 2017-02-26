package net.halalaboos.mcwrapper.api.entity;

import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface ItemPickup extends Entity {

	int getPickupDelay();

	ItemStack getItem();
}
