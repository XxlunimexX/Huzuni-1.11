package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.util.Rotation;
import net.halalaboos.mcwrapper.api.util.Vector3d;
import net.halalaboos.mcwrapper.api.world.Fluid;
import net.halalaboos.mcwrapper.api.world.World;
import net.minecraft.block.material.Material;
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
	@Shadow public float fallDistance;
	@Shadow public float stepHeight;

	@Shadow public abstract UUID getUniqueID();
	@Shadow public abstract String getName();
	@Shadow public abstract float getEyeHeight();
	@Shadow public abstract int getEntityId();
	@Shadow public abstract void setPosition(double x, double y, double z);
	@Shadow public abstract boolean isInWater();
	@Shadow public abstract boolean isInLava();
	@Shadow public abstract boolean isInsideOfMaterial(Material materialIn);

	@Shadow
	public abstract double getDistance(double x, double y, double z);

	@Shadow
	public boolean onGround;

	@Shadow
	public abstract void setSprinting(boolean sprinting);

	@Shadow
	public abstract boolean isSprinting();

	@Shadow
	public abstract boolean isSneaking();

	@Override
	public String getEntityName() {
		return getName();
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
	public Vector3d getLocation() {
		return new Vector3d(posX, posY, posZ);
	}

	@Override
	public Vector3d getPreviousLocation() {
		return new Vector3d(prevPosX, prevPosY, prevPosZ);
	}

	@Override
	public Vector3d getVelocity() {
		return new Vector3d(motionX, motionY, motionZ);
	}

	@Override
	public Rotation getRotation() {
		return new Rotation(rotationPitch, rotationYaw);
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public double getDistanceTo(Entity entity) {
		return getDistance(entity.getX(), entity.getY(), entity.getZ());
	}

	@Override
	public double getDistanceTo(Vector3d pos) {
		return getDistance(pos.x, pos.y, pos.z);
	}

	@Override
	public int getHurtResistantTime() {
		return hurtResistantTime;
	}

	@Override
	public boolean isInFluid(Fluid fluid) {
		return fluid == Fluid.WATER ? isInWater() : isInLava();
	}

	@Override
	public boolean isInsideOfWater() {
		return isInsideOfMaterial(Material.WATER);
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
	public float getEye() {
		return getEyeHeight();
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

	@Override
	public int getId() {
		return getEntityId();
	}

	@Override
	public void setLocation(Vector3d location) {
		setPosition(location.x, location.y, location.z);
	}

	@Override
	public void setRotation(Rotation rotation) {
		rotationPitch = rotation.pitch;
		rotationYaw = rotation.yaw;
	}

	@Override
	public double getX() {
		return posX;
	}

	@Override
	public double getY() {
		return posY;
	}

	@Override
	public double getZ() {
		return posZ;
	}

	@Override
	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	@Override
	public void setSprint(boolean sprint) {
		this.setSprinting(sprint);
	}

	@Override
	public boolean getSprinting() {
		return isSprinting();
	}

	@Override
	public boolean getSneaking() {
		return isSneaking();
	}
}
