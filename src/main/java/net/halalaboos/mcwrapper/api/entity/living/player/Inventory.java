package net.halalaboos.mcwrapper.api.entity.living.player;

import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Inventory {

	ItemStack getStack(int slot);

	int getSize();

}
