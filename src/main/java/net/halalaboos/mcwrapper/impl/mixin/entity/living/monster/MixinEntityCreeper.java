package net.halalaboos.mcwrapper.impl.mixin.entity.living.monster;

import net.halalaboos.mcwrapper.api.entity.living.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.monster.EntityCreeper.class)
public abstract class MixinEntityCreeper extends MixinEntityMob implements Creeper {

	@Shadow private int explosionRadius;
	@Shadow public abstract boolean getPowered();
	@Shadow public abstract int getCreeperState();

	@Override
	public int getRadius() {
		return explosionRadius;
	}

	@Override
	public boolean isPowered() {
		return getPowered();
	}

	@Override
	public State getState() {
		return getCreeperState() == -1 ? State.IDLE : State.FUSED;
	}
}
