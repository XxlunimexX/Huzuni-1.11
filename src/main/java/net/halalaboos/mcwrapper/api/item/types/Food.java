package net.halalaboos.mcwrapper.api.item.types;

import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.item.Item;
import net.halalaboos.mcwrapper.api.item.ItemStack;

/**
 * Created by Brandon Williams on 3/4/2017.
 */
public interface Food extends Item {

    /**
     * @return I don't know the use of this.
     * */
    float getSaturation(ItemStack itemStack);

    /**
     * @return The amount of hunger this food heals.
     * */
    int getHealth(ItemStack itemStack);

    /**
     * @return True if this food item can satisfy the living entities hunger.
     * */
    boolean satisfiesHunger(Living living, ItemStack itemStack);
}
