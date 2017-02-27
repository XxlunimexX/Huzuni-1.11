package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Bow;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemBow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemBow.class)
public abstract class MixinItemBow extends MixinItem implements Bow {
}
