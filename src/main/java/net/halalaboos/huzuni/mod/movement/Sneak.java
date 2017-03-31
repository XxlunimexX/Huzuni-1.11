package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.client.GameKeybind;
import net.halalaboos.mcwrapper.api.event.player.PostMotionUpdateEvent;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getSettings;

/**
 * Forces the player to sneak.
 */
public class Sneak extends BasicMod {
	
	public Sneak() {
		super("Sneak", "Forces you to sneak", Keyboard.KEY_Z);
		setAuthor("brudin");
		this.setCategory(Category.MOVEMENT);
		subscribe(PostMotionUpdateEvent.class, event -> getSettings().setKeyState(GameKeybind.SNEAK, true));
	}
	
	@Override
	public void onDisable() {
		getSettings().setKeyState(GameKeybind.SNEAK, false);
	}
}
