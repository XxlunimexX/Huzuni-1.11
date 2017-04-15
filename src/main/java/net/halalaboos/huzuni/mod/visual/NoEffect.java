package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.mcwrapper.api.client.ClientEffects;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;

/**
 * Allows the user to configure which {@link ClientEffects client-side effects } should be displayed in-game.
 *
 * @author b
 */
public class NoEffect extends BasicMod {

	/**
	 * @see ClientEffects#HURTCAM
	 */
	private Toggleable hurtcam = new Toggleable("No hurtcam", "Disable the screen from shaking when you take damage.");

	/**
	 * @see ClientEffects#WEATHER
	 */
	private Toggleable weather = new Toggleable("No weather", "Disable weather effects.");

	/**
	 * @see ClientEffects#OVERLAY
	 */
	private Toggleable overlay = new Toggleable("No overlay", "Disable fire/pumpkin overlay.");

	/**
	 * @see ClientEffects#BLINDNESS
	 */
	private Toggleable blindness = new Toggleable("No blindness", "Disable the blindness effect.");

	public NoEffect() {
		super("No effect", "Prevents various annoying effects such as hurtcam.");
		addChildren(hurtcam, weather, overlay, blindness);
		hurtcam.setEnabled(true);
		blindness.setEnabled(true);
		setAuthor("brudin");
		setCategory(Category.VISUAL);
		settings.setDisplayable(false);
		subscribe(PreMotionUpdateEvent.class, event -> {
			ClientEffects.BLINDNESS.setEnabled(!blindness.isEnabled());
			ClientEffects.HURTCAM.setEnabled(!hurtcam.isEnabled());
			ClientEffects.WEATHER.setEnabled(!weather.isEnabled());
			ClientEffects.OVERLAY.setEnabled(!overlay.isEnabled());
		});
	}

	@Override
	protected void onDisable() {
		ClientEffects.BLINDNESS.setEnabled(true);
		ClientEffects.HURTCAM.setEnabled(true);
		ClientEffects.WEATHER.setEnabled(true);
		ClientEffects.OVERLAY.setEnabled(true);
	}
}
