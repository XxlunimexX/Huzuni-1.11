package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.event.player.PostMotionUpdateEvent;

import static net.halalaboos.mcwrapper.api.MCWrapper.getInput;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Climbs up ladders at a faster rate.
 * */
public class Fastladder extends BasicMod {

	private final Value speed = new Value("Speed", 0.1F, 0.2F, 1F, 0.1F, "How fast you go up the ladder.");

	public Fastladder() {
		super("Fast ladder", "Allows you to climb ladders faster");
		setAuthor("brudin");
		addChildren(speed);
		this.setCategory(Category.MOVEMENT);
		subscribe(PostMotionUpdateEvent.class, event -> {
			float multiplier = speed.getValue();
			//If we are climbing and moving forward
			if (getPlayer().isClimbing() && getInput().getForward() != 0) {
				//Set the velocity's y-value to the player-set speed
				getPlayer().setVelocity(getPlayer().getVelocity().setY(multiplier));
			}
		});
	}
}
