package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Hoe;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemHoe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemHoe.class)
public abstract class MixinItemHoe extends MixinItem implements Hoe {
}
