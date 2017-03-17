package net.halalaboos.mcwrapper.api.entity.projectile;

import net.halalaboos.mcwrapper.api.entity.Entity;

public interface Arrow extends Entity {

	/**
	 * @return The Entity that shot the arrow.
	 */
	Entity getSource();

	/**
	 * @return Whether or not the arrow is in the ground.
	 */
	boolean isInGround();

}
