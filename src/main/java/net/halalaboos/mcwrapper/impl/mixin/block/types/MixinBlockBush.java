package net.halalaboos.mcwrapper.impl.mixin.block.types;

import net.halalaboos.mcwrapper.api.block.types.Bush;
import net.halalaboos.mcwrapper.impl.mixin.block.MixinBlock;
import net.minecraft.block.BlockBush;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockBush.class)
public abstract class MixinBlockBush extends MixinBlock implements Bush {
}
