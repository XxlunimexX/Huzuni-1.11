package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.event.UpdateEvent.Type;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import org.lwjgl.input.Keyboard;

public class Nofall extends BasicMod {
	
	public Nofall() {
		super("Nofall", "Prevents fall damage from occuring", Keyboard.KEY_N);
		setAuthor("Halalaboos");
		this.setCategory(Category.MOVEMENT);
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
		if (event.type == Type.PRE) {
			if (mc.player.fallDistance > 3) {
				mc.player.onGround = true;
			}	
		} else {
			if (mc.player.fallDistance > 3) {
				mc.player.onGround = false;
				mc.player.fallDistance = 0;
			}
		}
	}
}
