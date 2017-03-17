package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.world.Fluid;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

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
					(getPlayer().isInFluid(Fluid.LAVA) || getPlayer().isInFluid(Fluid.WATER))) {
				getPlayer().getVelocity().addY(0.03);
			}
		});
	}
}
