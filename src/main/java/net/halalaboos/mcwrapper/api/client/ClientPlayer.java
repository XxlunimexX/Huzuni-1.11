package net.halalaboos.mcwrapper.api.client;

import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;

/**
 * Represents the client-side {@link Player}.
 */
public interface ClientPlayer extends Player {

	/**
	 * Swings the specified {@link Hand}.
	 * @param hand The hand to swing.  On versions older than 1.9, it doesn't matter what hand is used.
	 */
	void swingItem(Hand hand);

	/**
	 * Closes the current (in-game) window.
	 */
	void closeWindow();

	/**
	 * @return Whether or not the player is currently flying.
	 */
	boolean isFlying();
	void setFlying(boolean flying);

	/**
	 * When the player is moving forward.
	 * @return The forward movement value.
	 */
	float getForwardMovement();

	/**
	 * Sets whether or not the player is sneaking.  To avoid method name conflicts with the vanilla sources,
	 * the name is <code>setSneak</code> rather than something like <code>setSneaking</code>.
	 */
	void setSneak(boolean sneak);

	/**
	 * @return The current server's brand.
	 */
	String getBrand();

	/**
	 * Sends the specified message in chat.
	 */
	void sendMessage(String message);

	/**
	 * Used to obtain information such as the GameProfile or ping of other players.
	 * This can also be used to check whether or not a player is a 'real' player.
	 *
	 * @param player The target player.
	 * @return The player's {@link PlayerInfo}.
	 */
	PlayerInfo getInfo(Player player);

	/**
	 * If an Item such as a consumable (food, potion, etc.) or something like a shield is being used by the Player,
	 * this returns true.
	 *
	 * @return If the Player is using an item.
	 */
	boolean isUsingItem();

	/**
	 * Sets whether or not the Player should slow down when using an item.
	 *
	 * @see #getItemUseSlowdown()
	 * @param slowdown Whether or not the player should slow down
	 */
	void setItemUseSlowdown(boolean slowdown);

	/**
	 * When the Player is using an Item, their movement is slowed down if this returns true.
	 *
	 * @return If the Player should slow down when using an item
	 * TODO: Better name
	 */
	boolean getItemUseSlowdown();

}
