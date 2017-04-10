package net.halalaboos.mcwrapper.api.item.types;

import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.item.Item;

public interface BlockItem extends Item {

	Block getBlockType();

}
