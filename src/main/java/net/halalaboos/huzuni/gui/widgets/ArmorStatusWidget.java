package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;
import net.halalaboos.huzuni.api.gui.widget.Glue;
import net.halalaboos.mcwrapper.api.inventory.Slot;
import net.halalaboos.mcwrapper.api.item.ItemStack;

import java.util.Optional;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Widget which renders the player's equipped armor.
 * */
public class ArmorStatusWidget extends BackgroundWidget {

	public ArmorStatusWidget(WidgetManager menuManager) {
		super("Armor Status", "Renders equipped armor", menuManager);
		if (glue.isLeft() || glue.isRight()) {
			this.setWidth(21);
			this.setHeight(84);
		} else {
			this.setWidth(84);
			this.setHeight(21);
		}
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
		float incrementX, incrementY;
		if (glue.isLeft() || glue.isRight()) {
			incrementX = 0;
			incrementY = 21;
		} else {
			incrementX = 21;
			incrementY = 0;
		}
		renderItemStack(getWearingArmor(0).getItem(), x + 2, y + 2);
		x += incrementX; y += incrementY;
		renderItemStack(getWearingArmor(1).getItem(), x + 2, y + 2);
		x += incrementX; y += incrementY;
		renderItemStack(getWearingArmor(2).getItem(), x + 2, y + 2);
		x += incrementX; y += incrementY;
		renderItemStack(getWearingArmor(3).getItem(), x + 2, y + 2);
	}
	
	@Override
	protected void updateGlue(Glue oldGlue, Glue newGlue) {
		if (newGlue.isRight() && !oldGlue.isRight()) {
			this.setX(this.getX() + 63);
		} else if (!newGlue.isRight() && oldGlue.isRight()) {
			this.setWidth(84);
			this.setHeight(21);
		}
	}
	
	@Override
	public void useGlue() {
		if (glue.isLeft() || glue.isRight()) {
			this.setWidth(21);
			this.setHeight(84);
		} else {
			this.setWidth(84);
			this.setHeight(21);
		}
		super.useGlue();
	}
	
	private Slot getWearingArmor(int armorType) {
		return getPlayer().getInventoryContainer().getSlotAt(5 + armorType);
	}

	/**
	 * Renders the item stack at the given position.
	 * */
	private void renderItemStack(Optional<ItemStack> stack, int x, int y) {
		if (!stack.isPresent())
			return;
		stack.get().renderInGui(x, y);
	}
}
