package net.halalaboos.mcwrapper.api.client;

public interface Controller {

	/**
	 * As the Player mines a block, their current block damage increases each tick.
	 * <p>Once this hits 1, the block will break (unless it was broken too quickly, then the server will reset
	 * it.</p>
	 *
	 * @return The current block damage
	 */
	float getBlockDamage();

	/**
	 *
	 * @param damage
	 */
	void setBlockDamage(float damage);

	int getHitDelay();

	void setHitDelay(int hitDelay);
}
