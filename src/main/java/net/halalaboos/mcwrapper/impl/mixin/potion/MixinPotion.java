package net.halalaboos.mcwrapper.impl.mixin.potion;

import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.potion.Potion.class)
public abstract class MixinPotion implements Potion {

	@Shadow public abstract String getName();

	@Shadow
	public abstract boolean isBadEffect();

	@Shadow
	public abstract boolean isBeneficial();

	@Shadow
	public abstract boolean hasStatusIcon();

	@Override
	public String name() {
		return I18n.format(getName());
	}

	@Override
	public Type getType() {
		return isBadEffect() ? Type.BAD : isBeneficial() ? Type.BENEFICIAL : Type.NONE;
	}

	@Override
	public boolean hasIcon() {
		return hasStatusIcon();
	}
}
