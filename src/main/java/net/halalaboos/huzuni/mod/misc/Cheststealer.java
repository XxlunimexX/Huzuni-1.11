package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.task.ClickTask;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.inventory.Inventory;
import net.halalaboos.mcwrapper.api.inventory.gui.ChestGui;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Steals items from within a chest once the chest was opened.
 * */
public class Cheststealer extends BasicMod {
		
	private ChestGui guiChest;
	
	private Inventory chest;
	
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
			if (mc.getScreen() instanceof ChestGui) {
				if (chest != null && guiChest != null) {
					if (!clickTask.hasClicks()) {
						getPlayer().closeWindow();
						chest = null;
						guiChest = null;
						huzuni.clickManager.withdrawTask(clickTask);
					}
				} else {
					guiChest = (ChestGui) mc.getScreen();
					chest = guiChest.getContainer().getLower();
					index = 0;
					windowId = guiChest.getContainer().getId();
					for (; index < chest.getSize(); index++) {
						if (chest.getStack(index).isPresent()) {
							clickTask.add(windowId, index, 0, 1);
						}
					}
					huzuni.clickManager.requestTask(this, clickTask);
				}
			}
		}
	}
}
