package net.halalaboos.mcwrapper.api.item;

public interface ItemStack {

	/**
	 * @return The amount of items in the stack.
	 */
	int getSize();

	/**
	 * @return The item that the stack consists of.
	 */
	Item getItemType();

	/**
	 * @return The name of the ItemStack.
	 */
	String getName();

	void renderInGui(int x, int y);
}
