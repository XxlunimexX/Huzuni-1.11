package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Throwable;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemEnderPearl;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEnderPearl.class)
public abstract class MixinItemEnderPearl extends MixinItem implements Throwable {
}
