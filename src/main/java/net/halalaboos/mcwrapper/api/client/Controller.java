package net.halalaboos.mcwrapper.api.client;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.GameType;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.util.enums.ActionResult;
import net.halalaboos.mcwrapper.api.util.enums.ClickType;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

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
	 * Sets the current block damage.  Setting this to 1 while in the process of breaking a block will destroy the
	 * block.
	 * @param damage New block damage (0..1)
	 */
	void setBlockDamage(float damage);

	/**
	 * When the Player breaks a block, there is a brief (5 tick) cool down before they can start breaking another one.
	 *
	 * @return The block hit delay
	 */
	int getHitDelay();

	/**
	 * Sets the block hit delay.  Setting this to 0 every tick will remove the hit delay entirely and allow the
	 * Player to break blocks quicker.
	 *
	 * @param hitDelay New block hit delay
	 */
	void setHitDelay(int hitDelay);

	/**
	 * If the Player is currently mining a block, this will be true.
	 *
	 * @return If the Player is mining a block
	 */
	boolean isHittingBlock();

	/**
	 * Depending on the Player's {@link net.halalaboos.mcwrapper.api.entity.living.player.GameType}, the reach distance
	 * for breaking blocks will be different.
	 *
	 * <p>If the Player is in {@link net.halalaboos.mcwrapper.api.entity.living.player.GameType#CREATIVE}, then
	 * their reach distance will be 5 blocks.  Otherwise, it'll be 4.5 blocks.</p>
	 *
	 * @return The block reach distance
	 */
	float getBlockReach();

	/**
	 * Resets the {@link #getBlockDamage()}
	 */
	void resetDigging();

	/**
	 * Used to sync the current items and process read packets.
	 */
	void update();

	/**
	 * Returns the Player's current game type/mode. (e.g. creative, survival, adventure)
	 *
	 * @return The current game mode of the Player
	 */
	GameType getGameType();

	/**
	 * Performed when the Player destroys a block.
	 *
	 * @param blockPosition The position of the destroyed block
	 * @return If the block was destroyed.
	 */
	boolean onBlockDestroy(Vector3i blockPosition);

	ActionResult rightClickBlock(Vector3i pos, Face direction, Vector3d vec, Hand hand);

	ItemStack clickSlot(int windowId, int slot, int mouseButton, ClickType type);

	ActionResult interactWith(Entity target, Hand hand);

	ActionResult rightClick(Hand hand);

	void attack(Entity target);

}
