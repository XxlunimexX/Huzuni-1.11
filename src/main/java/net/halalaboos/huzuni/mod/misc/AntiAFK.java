package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;

public class AntiAFK extends BasicMod {

	private net.halalaboos.huzuni.api.util.Timer timer = new Timer();

	public AntiAFK() {
		super("Anti AFK", "Prevents you from getting kicked for not moving.");
		setAuthor("brudin");
		setCategory(Category.MISC);
		subscribe(PreMotionUpdateEvent.class, event -> {
			if (timer.hasReach(Timer.TimeUnit.SECONDS, 10) && mc.getPlayer().isOnGround()) {
				mc.getPlayer().jump();
				timer.reset();
			}
		});
	}

}
