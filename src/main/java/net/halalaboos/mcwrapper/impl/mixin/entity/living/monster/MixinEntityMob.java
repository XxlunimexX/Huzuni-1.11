package net.halalaboos.mcwrapper.impl.mixin.entity.living.monster;

import net.halalaboos.mcwrapper.api.entity.living.monster.Monster;
import net.halalaboos.mcwrapper.impl.mixin.entity.living.MixinEntityCreature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(net.minecraft.entity.monster.EntityMob.class)
public abstract class MixinEntityMob extends MixinEntityCreature implements Monster {
}
