package net.halalaboos.mcwrapper.impl.mixin.inventory;

import net.halalaboos.mcwrapper.api.inventory.PlayerInventory;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayer implements PlayerInventory {

	@Shadow
	public abstract net.minecraft.item.ItemStack getStackInSlot(int index);

	@Shadow
	public abstract net.minecraft.item.ItemStack armorItemInSlot(int slotIn);

	@Shadow public int currentItem;

	@Shadow public abstract int getSlotFor(net.minecraft.item.ItemStack stack);

	@Shadow @Final public NonNullList<net.minecraft.item.ItemStack> mainInventory;

	@Override
	public Optional<ItemStack> getStack(int slot) {
		return Convert.getOptional(getStackInSlot(slot));
	}

	@Override
	public Optional<ItemStack> getArmorStack(int slot) {
		return Convert.getOptional(armorItemInSlot(slot));
	}

	@Override
	public int getCurrentSlot() {
		return currentItem;
	}

	@Override
	public void setCurrentSlot(int slot) {
		this.currentItem = slot;
	}
}
