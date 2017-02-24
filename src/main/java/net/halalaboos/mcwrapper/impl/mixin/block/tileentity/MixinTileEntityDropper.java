package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.Dropper;
import net.minecraft.tileentity.TileEntityDropper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TileEntityDropper.class)
public abstract class MixinTileEntityDropper extends MixinTileEntity implements Dropper {
}
