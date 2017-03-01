package net.halalaboos.mcwrapper.api.item;

import net.halalaboos.mcwrapper.api.MCWrapper;

public interface ItemStack {

	/**
	 * @return The amount of items in the stack.
	 */
	int getSize();

	int getMaxSize();

	/**
	 * @return The item that the stack consists of.
	 */
	Item getItemType();

	/**
	 * @return The name of the ItemStack.
	 */
	String getName();

	int getMaxUseTicks();

	boolean empty();

	//TEMP
	void renderInGui(int x, int y);

	//TEMP
	void render3D(int x, int y);

	static Builder getBuilder() {
		return MCWrapper.getAdapter().getBuilder(Builder.class);
	}

	static ItemStack from(Item item, int size) {
		return getBuilder().setItem(item).setSize(size).build();
	}

	interface Builder extends net.halalaboos.mcwrapper.api.util.Builder<ItemStack> {

		Builder setItem(Item item);

		Builder setSize(int size);

	}
}
