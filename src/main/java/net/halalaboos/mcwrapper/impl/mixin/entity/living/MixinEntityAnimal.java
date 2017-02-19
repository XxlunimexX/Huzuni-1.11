package net.halalaboos.mcwrapper.impl.mixin.entity.living;

import net.halalaboos.mcwrapper.api.entity.living.Animal;
import net.minecraft.entity.passive.EntityAnimal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAnimal.class)
public abstract class MixinEntityAnimal extends MixinEntityAgeable implements Animal {
}
