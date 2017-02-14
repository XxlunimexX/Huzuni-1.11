package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.event.UpdateEvent.Type;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import org.lwjgl.input.Keyboard;

/***
 * Swims for the player.
 */
public class Dolphin extends BasicMod {

	public Dolphin() {
		super("Dolphin", "Automagically swims once you enter the water", Keyboard.KEY_K);
		this.setCategory(Category.MOVEMENT);
		setAuthor("brudin");
	}
	
	@Override
	public void onEnable() {
		huzuni.eventManager.addListener(this);
	}
	
	@Override
	public void onDisable() {
		huzuni.eventManager.removeListener(this);
	}

	@EventMethod
	public void onUpdate(UpdateEvent event) {
		if (!mc.gameSettings.keyBindSneak.isPressed() && !mc.gameSettings.keyBindJump.isPressed() && (mc.player.isInWater() || mc.player.isInLava()) && event.type == Type.PRE) {
			mc.player.motionY += 0.03999999910593033;
		}
	}

}
