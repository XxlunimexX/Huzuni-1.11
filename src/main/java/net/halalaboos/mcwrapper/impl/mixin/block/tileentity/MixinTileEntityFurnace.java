package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.Furnace;
import net.minecraft.tileentity.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TileEntityFurnace.class)
public abstract class MixinTileEntityFurnace extends MixinTileEntity implements Furnace {
}
