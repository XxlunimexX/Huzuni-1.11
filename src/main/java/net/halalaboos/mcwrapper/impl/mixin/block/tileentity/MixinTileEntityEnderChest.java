package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.EnderChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TileEntityEnderChest.class)
public abstract class MixinTileEntityEnderChest extends MixinTileEntity implements EnderChest {
}
