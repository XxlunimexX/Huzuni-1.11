package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Seeds;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemSeeds;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemSeeds.class)
public abstract class MixinItemSeeds extends MixinItem implements Seeds {
}
