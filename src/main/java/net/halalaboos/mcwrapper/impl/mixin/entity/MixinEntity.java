package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.util.Transform;
import net.halalaboos.mcwrapper.api.util.Vector3d;
import net.halalaboos.mcwrapper.api.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(net.minecraft.entity.Entity.class) public abstract class MixinEntity implements Entity {

	@Shadow public net.minecraft.world.World world;

	@Shadow public double posX;
	@Shadow public double posY;
	@Shadow public double posZ;

	@Shadow public double prevPosX;
	@Shadow public double prevPosY;
	@Shadow public double prevPosZ;

	@Shadow public double motionX;
	@Shadow public double motionY;
	@Shadow public double motionZ;

	@Shadow public float rotationPitch;
	@Shadow public float rotationYaw;

	@Shadow public boolean isDead;

	@Shadow public int hurtResistantTime;

	@Shadow public float width;
	@Shadow public float height;

	@Shadow public abstract UUID getUniqueID();
	@Shadow public abstract String shadow$getName();
	@Shadow public abstract boolean shadow$isInWater();
	@Shadow public abstract float shadow$getEyeHeight();

	@Shadow
	public float fallDistance;

	@Shadow
	public float stepHeight;

	@Override
	public String getName() {
		return shadow$getName();
	}

	@Override
	public UUID getUUID() {
		return getUniqueID();
	}

	@Override
	public World getWorld() {
		return ((World) world);
	}

	@Override
	public Vector3d getPosition() {
		return new Vector3d(posX, posY, posZ);
	}

	@Override
	public Vector3d getPreviousPosition() {
		return new Vector3d(prevPosX, prevPosY, prevPosZ);
	}

	@Override
	public Vector3d getVelocity() {
		return new Vector3d(motionX, motionY, motionZ);
	}

	@Override
	public Transform getTransform() {
		return new Transform(rotationPitch, rotationYaw);
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	//todo
	@Override
	public double getDistanceTo(Entity entity) {
		return 0;
	}

	//todo
	@Override
	public double getDistanceTo(Vector3d pos) {
		return 0;
	}

	@Override
	public int getHurtResistantTime() {
		return hurtResistantTime;
	}

	@Override
	public boolean isInWater() {
		return shadow$isInWater();
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getEyeHeight() {
		return shadow$getEyeHeight();
	}

	@Override
	public float getFallDistance() {
		return fallDistance;
	}

	@Override
	public void setFallDistance(float fallDistance) {
		this.fallDistance = fallDistance;
	}

	@Override
	public float getStepHeight() {
		return stepHeight;
	}

	@Override
	public void setStepHeight(float stepHeight) {
		this.stepHeight = stepHeight;
	}
}
