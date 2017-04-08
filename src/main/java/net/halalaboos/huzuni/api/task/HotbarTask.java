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
			if (getPlayer().getStack(i).isPresent()) {
				ItemStack item = getPlayer().getStack(i).get();
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
		}
		slot = currentSlot;
		if (slot != -1) {
			if (slot != getPlayer().getPlayerInventory().getCurrentSlot()) {
				getPlayer().getPlayerInventory().setCurrentSlot(slot);
				getController().update();
			} else
				getPlayer().getPlayerInventory().setCurrentSlot(slot);
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
		return slot != -1 && getPlayer().getStack(slot).isPresent() && isValid(getPlayer().getStack(slot).get());
	}
}
