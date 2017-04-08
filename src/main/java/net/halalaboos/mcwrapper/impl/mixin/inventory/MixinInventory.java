package net.halalaboos.mcwrapper.impl.mixin.inventory;

import net.halalaboos.mcwrapper.api.inventory.Inventory;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(IInventory.class)
public interface MixinInventory extends Inventory {
	@Shadow int getSizeInventory();

	@Shadow net.minecraft.item.ItemStack getStackInSlot(int index);

	@Override
	default int getSize() {
		return getSizeInventory();
	}

	@Override
	default Optional<ItemStack> getStack(int slot) {
		return Convert.getOptional(getStackInSlot(slot));
	}
}
