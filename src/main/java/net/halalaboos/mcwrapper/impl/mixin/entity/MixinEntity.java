package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.util.math.Rotation;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.world.Fluid;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
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
	@Shadow	public boolean onGround;
	@Shadow public abstract void setSprinting(boolean sprinting);
	@Shadow public abstract boolean isSprinting();
	@Shadow public abstract boolean isSneaking();
	@Shadow public abstract boolean isInvisible();
	@Shadow public int ticksExisted;
	@Shadow public abstract ITextComponent getDisplayName();
	@Shadow public abstract AxisAlignedBB getEntityBoundingBox();
	@Shadow public boolean isCollidedHorizontally;
	@Shadow public boolean isCollided;
	@Shadow public boolean isCollidedVertically;

	@Shadow protected abstract void setRotation(float yaw, float pitch);

	private AABB aabb;

	@Inject(method = "setEntityBoundingBox", at = @At("HEAD"))
	public void setEntityBoundingBox(AxisAlignedBB bb, CallbackInfo ci) {
		this.aabb = Convert.from(bb);
	}

	@Override
	public String name() {
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
	public void setVelocity(Vector3d velocity) {
		this.motionX = velocity.getX();
		this.motionY = velocity.getY();
		this.motionZ = velocity.getZ();
	}

	@Override
	public Rotation getRotation() {
		return new Rotation(rotationPitch, rotationYaw);
	}

	@Override
	public float getPitch() {
		return rotationPitch;
	}

	@Override
	public float getYaw() {
		return rotationYaw;
	}

	@Override
	public void setPitch(float pitch) {
		this.rotationPitch = pitch;
	}

	@Override
	public void setYaw(float yaw) {
		this.rotationYaw = yaw;
	}

	@Override
	public void setRotation(Rotation rotation) {
		setRotation(rotation.yaw, rotation.pitch);
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public double getDistanceTo(Entity entity) {
		return getLocation().distanceTo(entity.getLocation());
	}

	@Override
	public double getDistanceTo(Vector3d pos) {
		return getLocation().distanceTo(pos);
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
		setPosition(location.getX(), location.getY(), location.getZ());
	}

	@Override
	public void setPitchYaw(float pitch, float yaw) {
		setRotation(yaw, pitch);
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

	@Override
	public String getCoordinates() {
		DecimalFormat format = new DecimalFormat("0.0");
		String x = format.format(getX());
		String y = format.format(getY());
		String z = format.format(getZ());
		return x + ", " + y + ", " + z;
	}

	@Override
	public boolean getInvisible() {
		return isInvisible();
	}

	@Override
	public int getExistedTicks() {
		return ticksExisted;
	}

	@Override
	public String getUnformattedName() {
		return getDisplayName().getUnformattedText();
	}

	@Override
	public String getFormattedName() {
		return getDisplayName().getFormattedText();
	}

	@Override
	public int getEntityListId() {
		return EntityList.getID((Class<? extends net.minecraft.entity.Entity>)(Object)getClass());
	}

	@Override
	public AABB getBoundingBox() {
		if (this.aabb == null) {
			return new AABB(0, 0, 0, 0, 0, 0);
		}
		return aabb;
	}

	@Override
	public boolean isCollided(CollisionType type) {
		return type == CollisionType.HORIZONTAL ? isCollidedHorizontally :
				type == CollisionType.VERTICAL ? isCollidedVertically : isCollided;
	}
}
