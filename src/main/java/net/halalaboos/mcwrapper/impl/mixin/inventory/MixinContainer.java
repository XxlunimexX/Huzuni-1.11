package net.halalaboos.mcwrapper.impl.mixin.inventory;

import net.halalaboos.mcwrapper.api.inventory.Container;
import net.halalaboos.mcwrapper.api.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.inventory.Container.class)
public abstract class MixinContainer implements Container {

	@Shadow public abstract net.minecraft.inventory.Slot getSlot(int slotId);

	@Shadow public int windowId;

	@Override
	public Slot getSlotAt(int index) {
		return ((Slot) getSlot(index));
	}

	@Override
	public int getId() {
		return windowId;
	}
}
