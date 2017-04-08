package net.halalaboos.mcwrapper.impl.mixin.inventory;

import net.halalaboos.mcwrapper.api.inventory.Slot;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.impl.Convert;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(net.minecraft.inventory.Slot.class)
public abstract class MixinSlot implements Slot {
	
	@Shadow @Final private int slotIndex;
	@Shadow public abstract net.minecraft.item.ItemStack getStack();
	@Shadow public abstract boolean getHasStack();

	@Shadow public int slotNumber;

	@Override
	public int getIndex() {
		return slotIndex;
	}

	@Override
	public Optional<ItemStack> getItem() {
		return Convert.getOptional(getStack());
	}

	@Override
	public int getSlotNumber() {
		return slotNumber;
	}
}
