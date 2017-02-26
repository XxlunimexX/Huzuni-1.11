package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.ItemPickup;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityItem.class)
public abstract class MixinEntityItem extends MixinEntity implements ItemPickup {

	@Shadow private int delayBeforeCanPickup;
	@Shadow public abstract net.minecraft.item.ItemStack getEntityItem();

	@Override
	public int getPickupDelay() {
		return delayBeforeCanPickup;
	}

	@Override
	public ItemStack getItem() {
		return (ItemStack)(Object)getEntityItem();
	}
}
