package net.halalaboos.mcwrapper.impl.registry;

import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;
import net.halalaboos.mcwrapper.api.registry.PotionRegistry;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class OnePointElevenPotionRegistry implements PotionRegistry {
	@Override
	public List<PotionEffect> getEffects(ItemStack stack) {
		return (List<PotionEffect>) (Object) PotionUtils.getEffectsFromStack((net.minecraft.item.ItemStack)(Object)stack);
	}

	@Override
	public Potion getPotion(String name) {
		return ((Potion) net.minecraft.potion.Potion.REGISTRY.getObject(new ResourceLocation(name)));
	}
}
