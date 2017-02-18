package net.halalaboos.mcwrapper.impl.mixin.world;

import net.halalaboos.mcwrapper.api.util.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.world.World.class) public abstract class MixinWorld implements World {

	@Shadow
	public abstract boolean setBlockToAir(BlockPos pos);

	@Override
	public void setToAir(Vector3i pos) {
		setBlockToAir(new BlockPos(pos.x, pos.y, pos.z));
	}
}
