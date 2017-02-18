package net.halalaboos.mcwrapper.impl.mixin.entity.living.monster;

import net.halalaboos.mcwrapper.api.entity.living.monster.Slime;
import net.halalaboos.mcwrapper.impl.mixin.entity.living.MixinEntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.monster.EntitySlime.class)
public abstract class MixinEntitySlime extends MixinEntityLiving implements Slime {

	@Shadow public abstract int getSlimeSize();

	@Override
	public int getSize() {
		return getSlimeSize();
	}

}
