package net.halalaboos.mcwrapper.api.registry;

import net.halalaboos.mcwrapper.api.item.enchant.Enchantment;

import java.util.Collection;

public interface EnchantmentRegistry {

	Collection<Enchantment> getEnchants();

	Enchantment getEnchant(String name);

	Enchantment getEnchant(int id);

	int getId(Enchantment enchantment);

}
