package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.EnchantingTable;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TileEntityEnchantmentTable.class)
public abstract class MixinTileEntityEnchantmentTable extends MixinTileEntity implements EnchantingTable {
}
