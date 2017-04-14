package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;

public class NoEffect extends BasicMod {

	private Toggleable hurtcam = new Toggleable("No hurtcam", "Disable the screen from shaking when you take damage");
	private Toggleable weather = new Toggleable("No weather", "Disable weather effects.");
	private Toggleable overlay = new Toggleable("No overlay", "Disable fire/pumpkin overlay");

	public NoEffect() {
		super("No effect", "Prevents various annoying effects such as hurtcam.");
		addChildren(hurtcam, weather, overlay);
		hurtcam.setEnabled(true);
		setAuthor("brudin");
		setCategory(Category.VISUAL);
		settings.setDisplayable(false);
		subscribe(PreMotionUpdateEvent.class, event -> {
			mc.setOverlayEnabled(!overlay.isEnabled());
			mc.setHurtcamEnabled(!hurtcam.isEnabled());
			mc.setWeatherEnabled(!weather.isEnabled());
		});
	}

	@Override
	protected void onToggle() {
		mc.setWeatherEnabled(true);
		mc.setHurtcamEnabled(true);
		mc.setOverlayEnabled(true);
	}
}
