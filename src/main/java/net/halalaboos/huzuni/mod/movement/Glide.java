package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.client.GameKeybind;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.world.Fluid;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getSettings;

/**
 * Allows the player to glide downward.
 * */
public class Glide extends BasicMod {

	private Value speed = new Value("Fall Speed", 1, 1, 10, "Fall speed multiplier");
	private Value distance = new Value("Minimum Distance", 0, 0, 10, "Minimum fall distance before gliding");

	public Glide() {
		super("Glide", "Allows an individual to glide down like an angel");
		setAuthor("brudin");
		this.setCategory(Category.MOVEMENT);
		addChildren(speed, distance);
		subscribe(PreMotionUpdateEvent.class, event -> {
			if (shouldGlide()) {
				getPlayer().setVelocity(getPlayer().getVelocity().setY(getGlideVelocity()));

				//Fun fact - search this number on github and see how many other clients use the same number! hehe
				getPlayer().setJumpMovementFactor(getPlayer().getJumpMovementFactor() * 1.21337F);
			}
		});
	}

	private double getGlideVelocity() {
		return -0.0315 * speed.getValue();
	}

	private boolean shouldGlide() {
		return 	!Flight.INSTANCE.isEnabled() && !getSettings().isKeyDown(GameKeybind.JUMP) &&
				getPlayer().getVelocity().getY() <= -getGlideVelocity() && !getPlayer().isOnGround() &&
				getPlayer().getFallDistance() >= distance.getValue() &&
				!getPlayer().isInFluid(Fluid.WATER) && !getPlayer().isClimbing() &&
				!getPlayer().isInFluid(Fluid.LAVA) && !getPlayer().isCollided(Entity.CollisionType.VERTICAL);
	}

}
