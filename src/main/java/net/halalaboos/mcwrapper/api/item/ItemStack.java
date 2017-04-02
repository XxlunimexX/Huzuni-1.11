package net.halalaboos.mcwrapper.api.item;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.attribute.Nameable;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.List;

/**
 * An ItemStack represents a 'stack' of {@link Item Items} in an inventory or the world.
 *
 * <p>Each ItemStack holds one type of {@link Item} and has a limited quantity (in vanilla context, the maximum quantity
 * for most ItemStacks is 64).</p>
 *
 * <p>To learn about creating new ItemStack instances, refer to the {@link Builder ItemStack Builder class}.</p>
 */
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

	/**
	 * Returns whether or not the {@link ItemStack} is empty or null.
	 *
	 * This is because as of recently, Minecraft's ItemStacks are non-null.  This isn't really necessary for versions
	 * such as 1.8, instead you would have to check whether not the Item is null.
	 */
	boolean empty();

	//TEMP
	void renderInGui(int x, int y);

	//TEMP
	void render3D(int x, int y);

	/**
	 * Returns the {@link Item}'s digging strength against the {@link Block} at the {@link Vector3i position}.
	 *
	 * @param pos The position of the {@link Block}
	 * @return The strength vs the {@link Block}
	 */
	float getStrength(Vector3i pos);

	/**
	 * Adds the specified Enchantment to the {@link Item}.
	 *
	 * @param enchantmentName The name of the Enchant
	 * @param level The Enchantment level
	 * TODO: Have an Enchantments enum or something instead?
	 */
	void addEnchant(String enchantmentName, short level);

	/**
	 * Returns a list of the {@link Item}'s enchants.
	 */
	List<String> getEnchants();

	/**
	 * Returns the {@link Item}'s metadata.
	 */
	int getData();

	static Builder getBuilder() {
		return MCWrapper.getAdapter().getBuilder(Builder.class);
	}

	/**
	 * Returns an {@link ItemStack} containing the specified {@link Item}.
	 *
	 * @param item The {@link Item} this stack contains.
	 * @param size The amount of the {@code item} this stack contains.
	 * @return The {@link ItemStack}
	 */
	static ItemStack from(Item item, int size) {
		return getBuilder().setItem(item).setSize(size).build();
	}

	/**
	 * Returns an {@link ItemStack} containing one of the specified {@link Item}.
	 *
	 * @param item The {@link Item} this stack contains.
	 * @return The {@link ItemStack}
	 */
	static ItemStack from(Item item) {
		return getBuilder().setItem(item).setSize(1).build();
	}

	/**
	 * Returns an {@link ItemStack} containing one of the specified {@link Block}.
	 *
	 * @param block The {@link Block} this stack contains.
	 * @return The {@link ItemStack}
	 */
	static ItemStack from(Block block) {
		return getBuilder().setItem(MCWrapper.getAdapter().getItemRegistry().getItem(block.getId())).setSize(1).build();
	}

	/**
	 * Returns an {@link ItemStack} containing the specified {@link Block}.
	 *
	 * @param block The {@link Block} this stack contains.
	 * @param size The amount of the {@code block} this stack contains.
	 * @return The {@link ItemStack}
	 */
	static ItemStack from(Block block, int size) {
		return getBuilder().setItem(MCWrapper.getAdapter().getItemRegistry().getItem(block.getId())).setSize(size).build();
	}

	/**
	 * Since our {@link ItemStack} class is an interface, we can't create ItemStack instances like vanilla Minecraft,
	 * e.g. {@code new ItemStack(Blocks.DIRT, 64)}.
	 *
	 * <p>The solution to this is to take an approach similar to the Sponge API and create a builder for ItemStacks
	 * that will work in the same way.</p>
	 *
	 * <p>This class is only meant to be used for version-specific implementations.  To create ItemStacks in your
	 * mod, refer to {@link #from(Item)}, which calls the {@link MCWrapper#getAdapter() adapter's} implementation.</p>
	 */
	interface Builder extends net.halalaboos.mcwrapper.api.util.Builder<ItemStack> {
		Builder setItem(Item item);
		Builder setSize(int size);
	}
}
