package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.TileEntity;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.tileentity.TileEntity.class)
public abstract class MixinTileEntity implements TileEntity {

	@Shadow public abstract BlockPos getPos();
	@Shadow public abstract Block getBlockType();
	@Shadow public abstract int getBlockMetadata();

	private Vector3i cachedPos;

	@Override
	public Vector3i getPosition() {
		if (cachedPos == null) {
			cachedPos = Convert.from(getPos());
		}
		return this.cachedPos;
	}
}
