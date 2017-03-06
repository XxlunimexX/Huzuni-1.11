package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.PotionItem;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemPotion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemPotion.class)
public abstract class MixinItemPotion extends MixinItem implements PotionItem {
}
