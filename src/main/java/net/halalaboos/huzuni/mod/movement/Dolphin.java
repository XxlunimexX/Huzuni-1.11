package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.client.GameKeybind;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.world.Fluid;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getSettings;

/***
 * Swims for the player.
 */
public class Dolphin extends BasicMod {

	public Dolphin() {
		super("Dolphin", "Automagically swims once you enter the water", Keyboard.KEY_K);
		this.setCategory(Category.MOVEMENT);
		setAuthor("brudin");
		subscribe(PreMotionUpdateEvent.class, event -> {
			if (!getSettings().isKeyDown(GameKeybind.SNEAK) && !getSettings().isKeyDown(GameKeybind.JUMP) &&
					(getPlayer().isInFluid(Fluid.LAVA) || getPlayer().isInFluid(Fluid.WATER))) {
				getPlayer().setVelocity(getPlayer().getVelocity().addY(0.025));
			}
		});
	}
}
