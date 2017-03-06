package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.GlassBottle;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemGlassBottle;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemGlassBottle.class)
public abstract class MixinItemGlassBottle extends MixinItem implements GlassBottle {
}
