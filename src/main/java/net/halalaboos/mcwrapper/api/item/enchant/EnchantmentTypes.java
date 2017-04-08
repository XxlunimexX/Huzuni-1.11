package net.halalaboos.mcwrapper.api.item.enchant;

import net.halalaboos.mcwrapper.api.MCWrapper;

@SuppressWarnings("WeakerAccess")
public class EnchantmentTypes {

	public static final Enchantment PROTECTION = getEnchant("protection");
	public static final Enchantment FIRE_PROTECTION = getEnchant("fire_protection");
	public static final Enchantment FEATHER_FALLING = getEnchant("feather_falling");
	public static final Enchantment BLAST_PROTECTION = getEnchant("blast_protection");
	public static final Enchantment PROJECTILE_PROTECTION = getEnchant("projectile_protection");
	public static final Enchantment RESPIRATION = getEnchant("respiration");
	public static final Enchantment AQUA_AFFINITY = getEnchant("aqua_affinity");
	public static final Enchantment THORNS = getEnchant("thorns");
	public static final Enchantment DEPTH_STRIDER = getEnchant("depth_strider");
	public static final Enchantment FROST_WALKER = getEnchant("frost_walker");
	public static final Enchantment BINDING_CURSE = getEnchant("binding_curse");
	public static final Enchantment SHARPNESS = getEnchant("sharpness");
	public static final Enchantment SMITE = getEnchant("smite");
	public static final Enchantment BANE_OF_ARTHROPODS = getEnchant("bane_of_arthropods");
	public static final Enchantment KNOCKBACK = getEnchant("knockback");
	public static final Enchantment FIRE_ASPECT = getEnchant("fire_aspect");
	public static final Enchantment LOOTING = getEnchant("looting");
	public static final Enchantment field_191530_r = getEnchant("sweeping");
	public static final Enchantment EFFICIENCY = getEnchant("efficiency");
	public static final Enchantment SILK_TOUCH = getEnchant("silk_touch");
	public static final Enchantment UNBREAKING = getEnchant("unbreaking");
	public static final Enchantment FORTUNE = getEnchant("fortune");
	public static final Enchantment POWER = getEnchant("power");
	public static final Enchantment PUNCH = getEnchant("punch");
	public static final Enchantment FLAME = getEnchant("flame");
	public static final Enchantment INFINITY = getEnchant("infinity");
	public static final Enchantment LUCK_OF_THE_SEA = getEnchant("luck_of_the_sea");
	public static final Enchantment LURE = getEnchant("lure");
	public static final Enchantment MENDING = getEnchant("mending");
	public static final Enchantment VANISHING_CURSE = getEnchant("vanishing_curse");
	
	private static Enchantment getEnchant(String name) {
		return MCWrapper.getAdapter().getEnchantmentRegistry().getEnchant(name);
	}
}
