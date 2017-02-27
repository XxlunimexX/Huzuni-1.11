package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.SplashPotion;
import net.minecraft.item.ItemSplashPotion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemSplashPotion.class)
public abstract class MixinItemSplashPotion extends MixinItemPotion implements SplashPotion {
}
