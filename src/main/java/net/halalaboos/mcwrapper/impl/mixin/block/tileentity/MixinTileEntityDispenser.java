package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.Dispenser;
import net.minecraft.tileentity.TileEntityDispenser;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TileEntityDispenser.class)
public abstract class MixinTileEntityDispenser extends MixinTileEntity implements Dispenser {
}
