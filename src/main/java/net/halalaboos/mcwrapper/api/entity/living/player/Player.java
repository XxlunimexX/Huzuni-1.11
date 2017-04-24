package net.halalaboos.mcwrapper.api.entity.living.player;

import com.mojang.authlib.GameProfile;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.inventory.Container;
import net.halalaboos.mcwrapper.api.inventory.PlayerInventory;
import net.halalaboos.mcwrapper.api.item.ItemStack;

import java.util.Optional;

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
	Optional<ItemStack> getStack(int slot);

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
	 * @return True if this player's food level is below it's max (20).
	 * */
	boolean isHungry();

	/**
	 * Saturation is increased whenever the Player eats food.  Once the saturation level is at zero, they will begin
	 * to start losing hunger.
	 *
	 * @return The saturation level
	 */
	float getSaturation();

	/**
	 * @return The Player's {@link GameProfile}
	 */
	GameProfile getProfile();

	Container getInventoryContainer();

	PlayerInventory getPlayerInventory();

	void setPushedByWater(boolean pushedByWater);

	/**
	 * Sets whether or not the player can clip through blocks.
	 *
	 * Keep in mind that in most cases, enabling this is a bad idea since the player will just fall through the world,
	 * or if you're in a multiplayer server, you'll just get pulled back.  This is only really useful in an instance
	 * where you're not sending motion updates, such as something like a Freecam mod.
	 */
	void setNoClip(boolean noClip);
}
