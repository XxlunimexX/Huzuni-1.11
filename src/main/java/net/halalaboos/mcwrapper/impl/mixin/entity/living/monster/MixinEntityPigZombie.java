package net.halalaboos.mcwrapper.impl.mixin.entity.living.monster;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.monster.ZombiePigman;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(net.minecraft.entity.monster.EntityPigZombie.class)
public abstract class MixinEntityPigZombie extends MixinEntityZombie implements ZombiePigman {

	@Shadow private int angerLevel;
	@Shadow private UUID angerTargetUUID;

	@Override
	public boolean isAngry() {
		return angerLevel > 0;
	}

	@Override
	public boolean isAngryAt(Entity entity) {
		return angerTargetUUID == entity.getUUID();
	}
}
