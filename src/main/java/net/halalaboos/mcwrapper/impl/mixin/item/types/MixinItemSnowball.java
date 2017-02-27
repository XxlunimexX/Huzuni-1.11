package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Snowball;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemSnowball;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemSnowball.class)
public abstract class MixinItemSnowball extends MixinItem implements Snowball {
}
