package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Egg;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemEgg;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEgg.class)
public abstract class MixinItemEgg extends MixinItem implements Egg {
}
