package net.halalaboos.mcwrapper.impl.mixin.block.types;

import net.halalaboos.mcwrapper.api.block.types.Stairs;
import net.halalaboos.mcwrapper.impl.mixin.block.MixinBlock;
import net.minecraft.block.BlockStairs;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockStairs.class)
public abstract class MixinBlockStairs extends MixinBlock implements Stairs {
}
