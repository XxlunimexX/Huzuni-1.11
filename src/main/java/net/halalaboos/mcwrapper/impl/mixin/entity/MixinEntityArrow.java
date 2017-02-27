package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.Arrow;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow extends MixinEntity implements Arrow {

	@Shadow public net.minecraft.entity.Entity shootingEntity;

	@Shadow protected boolean inGround;

	@Override
	public Entity getSource() {
		return ((Entity) shootingEntity);
	}

	@Override
	public boolean isInGround() {
		return inGround;
	}
}
