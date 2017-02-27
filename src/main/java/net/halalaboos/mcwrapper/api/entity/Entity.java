package net.halalaboos.mcwrapper.api.entity;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.attribute.Identifiable;
import net.halalaboos.mcwrapper.api.attribute.Nameable;
import net.halalaboos.mcwrapper.api.util.math.Rotation;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.world.Fluid;
import net.halalaboos.mcwrapper.api.world.World;

public interface Entity extends Identifiable, Nameable {

	/**
	 * The World the Entity is currently in.
	 */
	World getWorld();

	/**
	 * The location of the Entity in the world.
	 */
	Vector3d getLocation();

	/**
	 * Sets the Entity's location.
	 */
	void setLocation(Vector3d location);

	/**
	 * The previous position of the Entity, often used for interpolation.
	 */
	Vector3d getPreviousLocation();

	/**
	 * The velocity of the Entity (e.g. motionX, motionY, motionZ)
	 */
	Vector3d getVelocity();

	/**
	 * Sets the Entity's velocity.
	 */
	void setVelocity(Vector3d velocity);

	/**
	 * @return The X value of {@link #getLocation()}
	 */
	double getX();

	/**
	 * @return The Y value of {@link #getLocation()}
	 */
	double getY();

	/**
	 * @return The Z value of {@link #getLocation()}
	 */
	double getZ();

	/**
	 * The rotation of the Entity.
	 */
	Rotation getRotation();

	float getPitch();

	float getYaw();

	void setPitch(float pitch);

	void setYaw(float yaw);

	void setRotation(Rotation rotation);

	/**
	 * Whether or not the Entity is dead - if it is, then it will be removed from the World.
	 */
	boolean isDead();

	/**
	 * The distance to the entity.
	 */
	double getDistanceTo(Entity entity);

	/**
	 * The distance to the position.
	 */
	double getDistanceTo(Vector3d pos);

	/**
	 * The remaining time the Entity is immune to damage.
	 */
	int getHurtResistantTime();

	/**
	 * Whether or not the Entity is inside of the specified fluid.
	 */
	boolean isInFluid(Fluid fluid);

	/**
	 * @return Whether or not the Entity is inside of water.
	 */
	boolean isInsideOfWater();

	/**
	 * @return The width of the Entity.
	 */
	float getWidth();

	/**
	 * @return The height of the Entity.
	 */
	float getHeight();

	/**
	 * @return The Entity's eye level.
	 */
	float getEyeHeight();

	/**
	 * @return How far the Entity has fallen.
	 */
	float getFallDistance();

	/**
	 * Sets the Entity's {@link #getFallDistance()}.
	 */
	void setFallDistance(float fallDistance);

	/**
	 * @return The Entity's step height.
	 */
	float getStepHeight();

	/**
	 * Sets the Entity's {@link #getStepHeight()}
	 */
	void setStepHeight(float stepHeight);

	/**
	 * @return The Entity's ID.
	 */
	int getId();

	/**
	 * Sets the Entity's rotation (pitch/yaw)
	 */
	void setPitchYaw(float pitch, float yaw);

	/**
	 * @return Whether or not the Entity is touching the ground.
	 */
	boolean isOnGround();

	/**
	 * Sets if the Entity is on ground.
	 */
	void setOnGround(boolean onGround);

	/**
	 * Sets Entity's sprinting state.
	 */
	void setSprinting(boolean sprint);

	/**
	 * @return Whether or not the Entity is sprinting.
	 */
	boolean isSprinting();

	/**
	 * @return Whether or not the Entity is sneaking.
	 */
	boolean isSneaking();

	/**
	 * @return The Entity's position in a String format.
	 */
	String getCoordinates();

	/**
	 * @return The Entity's interpolated position, used for rendering.
	 */
	default Vector3d getInterpolatedPosition() {
		float delta = MCWrapper.getMinecraft().getDelta();
		return getPreviousLocation().add(getLocation().sub(getPreviousLocation()).scale(delta));
	}

	default Vector3d getRenderPosition() {
		Vector3d cam = MCWrapper.getMinecraft().getCamera();
		return getInterpolatedPosition().sub(cam);
	}

	/**
	 * @return Whether or not the Entity is currently invisible.
	 */
	boolean isInvisible();

	/**
	 * @return The amount of ticks the Entity has existed for.
	 */
	int getExistedTicks();

	/**
	 * @return The unformatted name of the Entity.
	 */
	String getUnformattedName();

	/**
	 * @return The formatted name of the Entity.
	 */
	String getFormattedName();

	/**
	 * @return The Entity's ID from Minecraft's EntityList class.
	 */
	int getEntityListId();

	/**
	 * @return The Entity's bounding box.
	 */
	AABB getBoundingBox();

	boolean isCollided(CollisionType type);

	enum CollisionType {
		HORIZONTAL, VERTICAL, BOTH
	}
}
