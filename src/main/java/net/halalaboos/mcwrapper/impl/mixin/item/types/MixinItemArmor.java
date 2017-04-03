package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.item.types.Armor;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemArmor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemArmor.class)
public abstract class MixinItemArmor extends MixinItem implements Armor {
}
