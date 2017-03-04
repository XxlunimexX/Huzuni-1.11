package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Value;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Climbs up ladders at a faster rate.
 * */
public class Fastladder extends BasicMod {

	private final Value speed = new Value("Speed", 0.05F, 0.25F, 1F, 0.05F, "How fast you go up the ladder.");

	public Fastladder() {
		super("Fast ladder", "Allows you to climb ladders faster");
		setAuthor("brudin");
		addChildren(speed);
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
        float multiplier = speed.getValue();
        //If we are climbing and moving forward
        if (getPlayer().isClimbing() && getPlayer().getForwardMovement() != 0) {
            //Set the velocity's y-value to the player-set speed
        	getPlayer().setVelocity(getPlayer().getVelocity().setY(multiplier));
        }
    }
}
