package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Seeds;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemSeedFood;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemSeedFood.class)
public abstract class MixinItemSeedFood extends MixinItem implements Seeds {
}
