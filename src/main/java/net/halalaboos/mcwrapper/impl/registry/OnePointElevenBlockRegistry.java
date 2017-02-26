package net.halalaboos.mcwrapper.impl.registry;

import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.registry.BlockRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;

public class OnePointElevenBlockRegistry implements BlockRegistry {

	private Collection<Block> registered;

	@Override
	public Block getBlock(String name) {
		return ((Block) net.minecraft.block.Block.getBlockFromName(name));
	}

	@Override
	public Block getBlock(int id) {
		return ((Block) net.minecraft.block.Block.getBlockById(id));
	}

	@Override
	public Collection<Block> getRegisteredBlocks() {
		if (registered == null) {
			Collection<Block> out = new ArrayList<>();
			for (ResourceLocation resourceLocation : net.minecraft.block.Block.REGISTRY.getKeys()) {
				net.minecraft.block.Block block = net.minecraft.block.Block.REGISTRY.getObject(resourceLocation);
				out.add((Block)block);
			}
			registered = out;
		}
		return registered;
	}
}
