package net.halalaboos.mcwrapper.api.item;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.attribute.Nameable;

public interface ItemStack extends Nameable {

	/**
	 * @return The amount of items in the stack.
	 */
	int getSize();

	int getMaxSize();

	/**
	 * @return The item that the stack consists of.
	 */
	Item getItemType();

	int getMaxUseTicks();

	boolean empty();

	//TEMP
	void renderInGui(int x, int y);

	//TEMP
	void render3D(int x, int y);

	float getStrength(int x, int y, int z);

	void addEnchant(String enchantmentName, short level);

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
