package net.halalaboos.mcwrapper.impl.mixin.entity.living.monster;

import net.halalaboos.mcwrapper.api.entity.living.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.monster.EntityZombie.class)
public abstract class MixinEntityZombie extends MixinEntityMob implements Zombie {

	@Shadow
	public abstract boolean isBreakDoorsTaskSet();

	@Override
	public boolean canBreakDoor() {
		return isBreakDoorsTaskSet();
	}
}
