package net.halalaboos.mcwrapper.api.item;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.attribute.Nameable;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.List;

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

	float getStrength(Vector3i pos);

	void addEnchant(String enchantmentName, short level);

	List<String> getEnchants();

	static Builder getBuilder() {
		return MCWrapper.getAdapter().getBuilder(Builder.class);
	}

	static ItemStack from(Item item, int size) {
		return getBuilder().setItem(item).setSize(size).build();
	}

	static ItemStack from(Item item) {
		return getBuilder().setItem(item).setSize(1).build();
	}

	static ItemStack from(Block block) {
		return getBuilder().setItem(MCWrapper.getAdapter().getItemRegistry().getItem(block.getId())).setSize(1).build();
	}

	static ItemStack from(Block block, int size) {
		return getBuilder().setItem(MCWrapper.getAdapter().getItemRegistry().getItem(block.getId())).setSize(size).build();
	}

	interface Builder extends net.halalaboos.mcwrapper.api.util.Builder<ItemStack> {
		Builder setItem(Item item);
		Builder setSize(int size);
	}
}
