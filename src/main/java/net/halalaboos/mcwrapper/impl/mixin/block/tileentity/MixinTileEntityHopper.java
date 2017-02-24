package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.Hopper;
import net.minecraft.tileentity.TileEntityHopper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TileEntityHopper.class)
public abstract class MixinTileEntityHopper extends MixinTileEntity implements Hopper {
}
