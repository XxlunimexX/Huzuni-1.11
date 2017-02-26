package net.halalaboos.mcwrapper.api.registry;

import net.halalaboos.mcwrapper.api.block.Block;

import java.util.Collection;

public interface BlockRegistry {

	Block getBlock(String name);

	Block getBlock(int id);

	Collection<Block> getRegisteredBlocks();
}
