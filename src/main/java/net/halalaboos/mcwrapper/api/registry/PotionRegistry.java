package net.halalaboos.mcwrapper.api.registry;

import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;

import java.util.List;

public interface PotionRegistry {

	List<PotionEffect> getEffects(ItemStack stack);

	Potion getPotion(String name);
}
