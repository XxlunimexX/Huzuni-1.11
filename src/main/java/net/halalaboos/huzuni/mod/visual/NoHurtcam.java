package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;

public class NoHurtcam extends BasicMod {

	public NoHurtcam() {
		super("No hurtcam", "Prevents the screen from shaking when taking damage.");
		setAuthor("brudin");
		setCategory(Category.VISUAL);
		settings.setDisplayable(false);
	}

	@Override
	protected void onToggle() {
		mc.setHurtcamEnabled(!isEnabled());
	}
}
