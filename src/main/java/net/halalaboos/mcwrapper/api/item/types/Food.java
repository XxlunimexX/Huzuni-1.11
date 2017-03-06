package net.halalaboos.mcwrapper.api.item.types;

import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.item.Item;

/**
 * Created by Brandon Williams on 3/4/2017.
 */
public interface Food extends Item {

	/**
	 * When a Player eats Food, their saturation level will increase based on the amount that the food will provide.
	 *
	 * <p>Saturation will deplete before hunger does, meaning different types of food can make you not hungry for
	 * longer periods of time.</p>
	 *
	 * @return The saturation of the food
	 */
    float getSaturation();

	/**
	 * Represents the amount of hunger that will be restored upon eating.
	 *
	 * @return The health/restoration amount of the food
	 */
	int getHealth();

    /**
     * @return True if this food item can satisfy the living entities hunger.
     * */
    boolean satisfiesHunger(Player player);
}
