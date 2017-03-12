package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import org.lwjgl.input.Keyboard;

/***
 * Swims for the player.
 */
public class Dolphin extends BasicMod {

	public Dolphin() {
		super("Dolphin", "Automagically swims once you enter the water", Keyboard.KEY_K);
		this.setCategory(Category.MOVEMENT);
		setAuthor("brudin");
		subscribe(PreMotionUpdateEvent.class, event -> {
			if (!mc.gameSettings.keyBindSneak.isPressed() && !mc.gameSettings.keyBindJump.isPressed() &&
					(mc.player.isInWater() || mc.player.isInLava())) {
				mc.player.motionY += 0.03999999910593033;
			}
		});
	}
}
