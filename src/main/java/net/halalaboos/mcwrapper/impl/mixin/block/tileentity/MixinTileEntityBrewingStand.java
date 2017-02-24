package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.BrewingStand;
import net.minecraft.tileentity.TileEntityBrewingStand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TileEntityBrewingStand.class)
public abstract class MixinTileEntityBrewingStand extends MixinTileEntity implements BrewingStand {
}
