package net.halalaboos.mcwrapper.impl.mixin.entity.living;

import net.halalaboos.mcwrapper.api.entity.living.Creature;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.EntityCreature.class)
public abstract class MixinEntityCreature extends MixinEntityLiving implements Creature {

	@Shadow
	public abstract BlockPos getHomePosition();

	@Override
	public Vector3i getHome() {
		BlockPos pos = getHomePosition();
		return new Vector3i(pos.getX(), pos.getY(), pos.getZ());
	}
}
