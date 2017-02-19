package net.halalaboos.mcwrapper.impl.mixin.item;

import net.halalaboos.mcwrapper.api.item.Item;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.item.ItemStack.class)
public abstract class MixinItemStack implements ItemStack {

	@Shadow public abstract int getCount();
	@Shadow public abstract net.minecraft.item.Item getItem();
	@Shadow public abstract String getDisplayName();

	@Override
	public int getSize() {
		return getCount();
	}

	@Override
	public Item getItemType() {
		return ((Item) getItem());
	}

	@Override
	public String getName() {
		return getDisplayName();
	}
}
