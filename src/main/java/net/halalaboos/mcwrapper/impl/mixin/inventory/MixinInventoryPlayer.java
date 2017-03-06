package net.halalaboos.mcwrapper.impl.mixin.inventory;

import net.halalaboos.mcwrapper.api.inventory.PlayerInventory;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayer implements PlayerInventory {

	@Shadow
	public abstract net.minecraft.item.ItemStack getStackInSlot(int index);

	@Override
	public ItemStack getStack(int slot) {
		return ((ItemStack)(Object) getStackInSlot(slot));
	}
}
