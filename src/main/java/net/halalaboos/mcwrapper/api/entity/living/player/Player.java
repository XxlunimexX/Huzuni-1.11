package net.halalaboos.mcwrapper.api.entity.living.player;

import com.mojang.authlib.GameProfile;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.item.ItemStack;

/**
 * Represents a Player (humanoid) entity.
 */
public interface Player extends Living {

	/**
	 * @return The player's attack strength.  On versions older than 1.9, this will always return 1.
	 */
	float getAttackStrength();

	/**
	 * @param slot The slot in the hotbar
	 * @return The {@link ItemStack} in the specified slot.
	 */
	ItemStack getStack(int slot);

	/**
	 * Many servers spawn 'fake' players for things such as anti-cheating purposes.  This can cause problems with
	 * mods because they will target these fake players, so this will help prevent that from happening.
	 *
	 * @return Whether or not the Player is a fake player.
	 */
	boolean isNPC();

	/**
	 * @return The food level
	 */
	float getFood();

	/**
	 * @return The saturation level
	 */
	float getSaturation();

	GameProfile getProfile();

}
