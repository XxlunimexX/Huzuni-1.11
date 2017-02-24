package net.halalaboos.mcwrapper.impl.mixin.block.tileentity;

import net.halalaboos.mcwrapper.api.block.tileentity.Chest;
import net.minecraft.tileentity.TileEntityChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntityChest.class)
public abstract class MixinTileEntityChest extends MixinTileEntity implements Chest {

	@Shadow public TileEntityChest adjacentChestZNeg;
	@Shadow public TileEntityChest adjacentChestXPos;
	@Shadow public TileEntityChest adjacentChestXNeg;
	@Shadow public TileEntityChest adjacentChestZPos;

	@Override
	public Chest getAdjacentZNeg() {
		return ((Chest) adjacentChestZNeg);
	}

	@Override
	public Chest getAdjacentXPos() {
		return ((Chest) adjacentChestXPos);
	}

	@Override
	public Chest getAdjacentXNeg() {
		return ((Chest) adjacentChestXNeg);
	}

	@Override
	public Chest getAdjacentZPos() {
		return ((Chest) adjacentChestZPos);
	}
}
