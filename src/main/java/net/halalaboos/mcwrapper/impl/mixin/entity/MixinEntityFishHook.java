package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.FishHook;
import net.minecraft.entity.projectile.EntityFishHook;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityFishHook.class)
public abstract class MixinEntityFishHook extends MixinEntity implements FishHook {

}
