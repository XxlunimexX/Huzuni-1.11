package net.halalaboos.mcwrapper.impl.mixin.block.types;

import net.halalaboos.mcwrapper.api.block.types.SoulSand;
import net.minecraft.block.BlockSoulSand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockSoulSand.class)
public abstract class MixinBlockSoulSand implements SoulSand {
}
