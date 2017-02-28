package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Value;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * Modifies the timer speed of the game.
 * */
public class Timer extends BasicMod {
	
	public final Value speed = new Value("Multiplier", "", 0.1F, 1F, 5F, 0.1F, "Timer speed multiplier");

	public Timer() {
		super("Timer", "Allows you to adjust the in-game clock speed");
		this.setCategory(Category.MISC);
		setAuthor("brudin");
		addChildren(speed);
	}
	
	@Override
	public void onEnable() {
		huzuni.eventManager.addListener(this);
		getMinecraft().setTimerSpeed(speed.getValue());
	}
	
	@Override
	public void onDisable() {
		huzuni.eventManager.removeListener(this);
		getMinecraft().setTimerSpeed(1);
	}

	@EventMethod
	public void onUpdate(UpdateEvent event) {
		if (mc.currentScreen == null) {
			getMinecraft().setTimerSpeed(speed.getValue());
		} else {
			getMinecraft().setTimerSpeed(1);
		}
	}
}
