package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.ExperienceOrb;
import net.minecraft.entity.item.EntityXPOrb;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityXPOrb.class)
public abstract class MixinEntityXPOrb extends MixinEntity implements ExperienceOrb {
}
