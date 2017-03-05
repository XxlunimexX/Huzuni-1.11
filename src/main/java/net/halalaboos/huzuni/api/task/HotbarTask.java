package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.node.Nameable;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import static net.halalaboos.mcwrapper.api.MCWrapper.*;

public abstract class HotbarTask extends BasicTask {

	protected int slot = -1;

	public HotbarTask(Nameable handler) {
		super(handler);
		addDependency("hotbar");
	}


	@Override
	public void onPreUpdate() {
		ItemStack current = null;
		int currentSlot = -1;
		for (int i = 0; i < 9; i++) {
			ItemStack item = getPlayer().getStack(i);
			if (current != null) {
				if (compare(current, item)) {
					current = item;
					currentSlot = i;
				}
			} else {
				if (isValid(item)) {
					current = item;
					currentSlot = i;
				}
			}
		}
		slot = currentSlot;
		if (slot != -1) {
			if (slot != mc.player.inventory.currentItem) {
				mc.player.inventory.currentItem = slot;
				mc.playerController.updateController();
			} else
				mc.player.inventory.currentItem = slot;
		}
	}
	
	@Override
	public void onPostUpdate() {
	}

	@Override
	public void onTaskCancelled() {
	}
	
	protected abstract boolean isValid(ItemStack itemStack);
	
	protected boolean compare(ItemStack currentItem, ItemStack newItem) {
		return newItem != null && currentItem.getSize() > newItem.getSize();
	}
	
	public boolean hasSlot() {
		return slot != -1 && isValid(getPlayer().getStack(slot));
	}
}
