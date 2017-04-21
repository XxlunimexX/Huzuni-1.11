package net.halalaboos.mcwrapper.api.entity;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.MinecraftClient;
import net.halalaboos.mcwrapper.api.attribute.Identifiable;
import net.halalaboos.mcwrapper.api.attribute.Nameable;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.*;
import net.halalaboos.mcwrapper.api.world.Fluid;
import net.halalaboos.mcwrapper.api.world.World;

import java.util.Optional;

public interface Entity extends Identifiable, Nameable {

	/**
	 * The World the Entity is currently in.
	 */
	World getWorld();

	/**
	 * The location of the Entity in the world.
	 */
	Vector3d getLocation();

	Vector3i getBlockPosition();

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
	 * The distance to the position.
	 */
	default int getDistanceTo(Vector3i pos) {
		return MathUtils.ceil(getDistanceTo(pos.toDouble()));
	}

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
	 * The Entity's position in a displayable format.
	 *
	 * <p>If you were to display Entity.getX(), it would result in a number like 1.10000002; this will format each
	 * coordinate to a X.Y format.</p>
	 *
	 * @return x, y, z
	 */
	String getCoordinates();

	/**
	 * In the context of render-related mods that work with target Entity locations, you <i>could</i> just use the
	 * {@link #getLocation()}, but it won't be as smooth - the positioning of something like a line pointing to
	 * that location will be a bit choppy.
	 *
	 * What this does is return an interpolated version of the Entity's {@link #getLocation()},
	 * by using the {@link #getPreviousLocation()} and the {@link MinecraftClient#getDelta()}.
	 *
	 * @return The Entity's interpolated position.
	 */
	default Vector3d getInterpolatedPosition() {
		float delta = MCWrapper.getMinecraft().getDelta();
		return getPreviousLocation().add(getLocation().sub(getPreviousLocation()).scale(delta));
	}

	/**
	 * Subtracts the {@link #getInterpolatedPosition()} from the {@link MinecraftClient#getCamera() camera's location},
	 * which returns the render position on the screen.
	 *
	 * @return The Entity's render position
	 */
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

	/**
	 * Whether or not the Entity is collided on the given {@link CollisionType}.
	 * @param type The collision type, e.g. horizontal/vertical
	 *
	 * @return If the Entity is collided
	 */
	boolean isCollided(CollisionType type);

	/**
	 * Whether or not the Entity is currently riding another Entity.
	 *
	 * @return If the Entity is riding
	 */
	boolean isRiding();

	/**
	 * Currently, this just provides the name of the biome that the Entity is located in; if there is a need
	 * for more biome-related data, then this will be a bit more advanced.
	 *
	 * @return The current biome's name
	 */
	String getCurrentBiome();

	/**
	 * @return The Entity's (horizontal) {@link Face} direction.
	 */
	Face getFace();

	boolean isOnFire();

	Optional<Result> calculateIntercept(Vector3d expansion, Vector3d start, Vector3d end);

	enum CollisionType {
		HORIZONTAL, VERTICAL, BOTH
	}
}
