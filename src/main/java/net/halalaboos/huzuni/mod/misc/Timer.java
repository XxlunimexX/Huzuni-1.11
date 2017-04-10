package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;

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
		subscribe(PreMotionUpdateEvent.class, event -> {
			if (!getMinecraft().isScreenOpen()) {
				//If we don't have a screen open, change the speed
				getMinecraft().setTimerSpeed(speed.getValue());
			} else {
				//If we do have a screen open, temporarily revert it to the normal speed.
				getMinecraft().setTimerSpeed(1);
			}
		});
	}
	
	@Override
	public void onEnable() {
		getMinecraft().setTimerSpeed(speed.getValue());
	}
	
	@Override
	public void onDisable() {
		getMinecraft().setTimerSpeed(1);
	}
}
