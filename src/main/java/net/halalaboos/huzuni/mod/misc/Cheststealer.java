package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.task.ClickTask;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Steals items from within a chest once the chest was opened.
 * */
public class Cheststealer extends BasicMod {
		
	private GuiChest guiChest;
	
	private IInventory chest;
	
	private int windowId, index;

	private final ClickTask clickTask = new ClickTask(this);
	
	public Cheststealer() {
		super("Chest stealer", "Automagically steal every item from a chest inventory");
		setAuthor("Halalaboos");
		this.setCategory(Category.MISC);
		huzuni.clickManager.registerTaskHolder(this);
		subscribe(PreMotionUpdateEvent.class, this::onPreUpdate);
	}
	
	@Override
	public void onDisable() {
		huzuni.clickManager.withdrawTask(clickTask);
	}

	private void onPreUpdate(PreMotionUpdateEvent event) {
		if (huzuni.clickManager.hasPriority(this)) {
			if (mc.currentScreen instanceof GuiChest) {
				if (chest != null && guiChest != null) {
					if (!clickTask.hasClicks()) {
						mc.player.closeScreen();
						chest = null;
						guiChest = null;
						huzuni.clickManager.withdrawTask(clickTask);
					}
				} else {
					guiChest = (GuiChest) mc.currentScreen;
					chest = ((ContainerChest) guiChest.inventorySlots).getLowerChestInventory();
					index = 0;
					windowId = guiChest.inventorySlots.windowId;
					for (; index < chest.getSizeInventory(); index++) {
						ItemStack item = chest.getStackInSlot(index);
						if (item.isEmpty())
							continue;
						clickTask.add(windowId, index, 0, 1);
					}
					huzuni.clickManager.requestTask(this, clickTask);
				}
			}
		}
	}
}
