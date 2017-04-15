package net.halalaboos.mcwrapper.api.registry;

import net.halalaboos.mcwrapper.api.block.Block;

import java.util.Collection;

/**
 * Provides access to Minecraft's Block Registry to easily obtain {@link Block} instances by name, ID, etc.
 */
public interface BlockRegistry {

	/**
	 * Returns a {@link Block} with the provided {@code name}.
	 *
	 * @param name The name of the Block
	 * @return The Block
	 */
	Block getBlock(String name);

	/**
	 * Returns a {@link Block} with the provided {@code id}.
	 *
	 * @param id The ID of the Block
	 * @return The Block
	 */
	Block getBlock(int id);

	/**
	 * Contains all of the registered Blocks in-game.
	 *
	 * Not sure if it functions well with mods, will have to check later!
	 *
	 * @return All of the registered Blocks.
	 */
	Collection<Block> getRegisteredBlocks();
}
