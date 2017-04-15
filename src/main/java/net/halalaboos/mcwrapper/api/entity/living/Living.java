package net.halalaboos.mcwrapper.api.entity.living;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.data.HealthData;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;

import java.util.Collection;
import java.util.Optional;

/**
 * A Living entity is an entity that has health, can take damage, etc.
 *
 * Examples of a Living entity would be {@link Monster monsters}, {@link Animal animals},
 * {@link net.halalaboos.mcwrapper.api.entity.living.player.Player} players, and so on.
 */
public interface Living extends Entity {

	/**
	 * @return The health information for the Entity.
	 */
	HealthData getHealthData();

	/**
	 * Makes the Entity jump.
	 */
	void jump();

	/**
	 * @return Whether or not the Entity is climbing on a block such as a ladder or vine.
	 */
	boolean isClimbing();

	/**
	 * @param hand On versions older than 1.9, it doesn't matter what hand is used.
	 * @return The {@link ItemStack} in the specified hand.
	 */
	Optional<ItemStack> getHeldItem(Hand hand);

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

	/**
	 * When an Entity is using an item, this value goes up each tick.
	 *
	 * @return The item use ticks
	 */
	int getItemUseTicks();

	/**
	 * When an Entity gets a potion effect (e.g. night vision, swiftness), it will be added to this list until the
	 * effect timer runs out.
	 *
	 * @return The active potion effects.
	 */
	Collection<PotionEffect> getEffects();

	int getTotalArmor();

	/**
	 * Adds the specified {@link PotionEffect} to the Entity.
	 *
	 * @param effect The effect to add
	 */
	void addEffect(PotionEffect effect);

	/**
	 * Removes the specified {@link Potion} from the Entity.
	 *
	 * @param potion The potion to remove.
	 */
	void removeEffect(Potion potion);

	/**
	 * Returns the active {@link Hand}.  For example, if you have a Bow in one hand and Food in another, and held right
	 * click, using this would be an easy way of seeing what hand is being used.
	 */
	Hand getCurrentHand();
}
