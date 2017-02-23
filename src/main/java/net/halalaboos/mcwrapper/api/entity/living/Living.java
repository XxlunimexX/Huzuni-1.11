package net.halalaboos.mcwrapper.api.entity.living;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.data.HealthData;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Living extends Entity {

	/**
	 * @return The health information for the Entity.
	 */
	HealthData getHealthData();

	/**
	 * Makes the Entity jump.
	 */
	void doJump();

	/**
	 * @return Whether or not the Entity is climbing on a block such as a ladder or vine.
	 */
	boolean isClimbing();

	/**
	 * @param hand On versions older than 1.9, it doesn't matter what hand is used.
	 * @return The {@link ItemStack} in the specified hand.
	 */
	ItemStack getHeldItem(Hand hand);

	/**
	 * When an Entity is attacked, they are given a brief amount of invulnerability.  This represents the maximum time
	 * they can be invulnerable.
	 *
	 * @return The maximum invulnerability period
	 */
	int getMaxHurtResistantTime();

	/**
	 * Sets the distance multiplier that the Entity can jump.
	 *
	 * @param movementFactor How far the Entity will move each tick when jumping.
	 */
	void setJumpMovementFactor(float movementFactor);

	/**
	 * Represents how far the Entity will move each tick when jumping.
	 */
	float getJumpMovementFactor();
}
