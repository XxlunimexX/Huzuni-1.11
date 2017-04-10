package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.item.types.BlockItem;
import net.halalaboos.mcwrapper.impl.Convert;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemBlock.class)
public abstract class MixinItemBlock extends MixinItem implements BlockItem {
	@Shadow public abstract net.minecraft.block.Block getBlock();

	@Override
	public Block getBlockType() {
		return Convert.from(getBlock());
	}
}
