package net.halalaboos.mcwrapper.impl.registry;

import net.halalaboos.mcwrapper.api.item.Enchantment;
import net.halalaboos.mcwrapper.api.registry.EnchantmentRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;

public class OnePointElevenEnchantmentRegistry implements EnchantmentRegistry {

	private Collection<Enchantment> registered;

	@Override
	public Collection<Enchantment> getEnchants() {
		if (registered == null) {
			Collection<Enchantment> out = new ArrayList<>();
			for (ResourceLocation resourceLocation : net.minecraft.enchantment.Enchantment.REGISTRY.getKeys()) {
				net.minecraft.enchantment.Enchantment enchantment = net.minecraft.enchantment.Enchantment.REGISTRY.getObject(resourceLocation);
				out.add((Enchantment)enchantment);
			}
			registered = out;
		}
		return registered;
	}
}
