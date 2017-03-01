package net.halalaboos.mcwrapper.impl.builder;

import net.halalaboos.mcwrapper.api.item.Item;
import net.halalaboos.mcwrapper.api.item.ItemStack;

public class ItemStackBuilder implements ItemStack.Builder {

	private Item type;
	private int size;

	@Override
	public ItemStack build() {
		return (ItemStack) (Object) new net.minecraft.item.ItemStack((net.minecraft.item.Item) this.type, this.size);
	}

	@Override
	public ItemStack.Builder setItem(Item item) {
		this.type = item;
		return this;
	}

	@Override
	public ItemStack.Builder setSize(int size) {
		this.size = size;
		return this;
	}
}
