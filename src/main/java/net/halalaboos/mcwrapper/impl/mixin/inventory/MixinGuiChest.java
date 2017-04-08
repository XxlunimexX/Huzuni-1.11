package net.halalaboos.mcwrapper.impl.mixin.inventory;

import net.halalaboos.mcwrapper.api.inventory.ChestContainer;
import net.halalaboos.mcwrapper.api.inventory.Inventory;
import net.halalaboos.mcwrapper.api.inventory.gui.ChestGui;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiChest.class)
public class MixinGuiChest implements ChestGui {
	@Shadow @Final private IInventory lowerChestInventory;

	@Shadow @Final private IInventory upperChestInventory;

	@Override
	public Inventory getLower() {
		return Convert.from(lowerChestInventory);
	}

	@Override
	public Inventory getUpper() {
		return Convert.from(upperChestInventory);
	}

	@Override
	public ChestContainer getContainer() {
		return (ChestContainer)((GuiChest)(Object)this).inventorySlots;
	}

}
