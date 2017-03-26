package net.halalaboos.mcwrapper.impl.mixin.item;

import net.halalaboos.mcwrapper.api.item.Enchantment;
import org.spongepowered.asm.mixin.*;

@Mixin(net.minecraft.enchantment.Enchantment.class)
@Implements(@Interface(iface = Enchantment.class, prefix = "api$"))
public abstract class MixinEnchantment implements Enchantment {

	@Shadow public abstract String getName();
	@Shadow public abstract int shadow$getMaxLevel();
	@Shadow public abstract String getTranslatedName(int level);

	@Override
	public String name() {
		return getName();
	}

	@Override
	public String name(int level) {
		return getTranslatedName(level);
	}

	@Intrinsic
	public int api$getMaxLevel() {
		return shadow$getMaxLevel();
	}
}
