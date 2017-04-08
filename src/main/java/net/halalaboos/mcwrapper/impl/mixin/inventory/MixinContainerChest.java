package net.halalaboos.mcwrapper.impl.mixin.inventory;

import net.halalaboos.mcwrapper.api.inventory.ChestContainer;
import net.halalaboos.mcwrapper.api.inventory.Inventory;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerChest.class)
public abstract class MixinContainerChest extends MixinContainer implements ChestContainer {

	@Shadow @Final private IInventory lowerChestInventory;

	@Override
	public Inventory getLower() {
		return Convert.from(lowerChestInventory);
	}
}
