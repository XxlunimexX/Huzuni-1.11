package net.halalaboos.mcwrapper.impl.mixin.block.types;

import net.halalaboos.mcwrapper.api.block.types.Farmland;
import net.halalaboos.mcwrapper.impl.mixin.block.MixinBlock;
import net.minecraft.block.BlockFarmland;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockFarmland.class)
public abstract class MixinBlockFarmland extends MixinBlock implements Farmland {
}
