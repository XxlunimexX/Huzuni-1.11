package net.halalaboos.mcwrapper.impl.mixin.entity.liviing;

import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.impl.mixin.entity.MixinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.EntityLivingBase.class)
public abstract class MivinEntityLiving extends MixinEntity implements Living {

	@Shadow public abstract float shadow$getHealth();
	@Shadow public abstract float shadow$getMaxHealth();
	@Shadow protected abstract void shadow$jump();

	@Override
	public double getHealth() {
		return shadow$getHealth();
	}

	@Override
	public double getMaxHealth() {
		return shadow$getMaxHealth();
	}

	@Override
	public void jump() {
		shadow$jump();
	}
}
