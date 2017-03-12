package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.world.Fluid;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Allows the player to glide downward.
 * */
public class Glide extends BasicMod {
	
	public Glide() {
		super("Glide", "Allows an individual to glide down like an angel");
		setAuthor("brudin");
		this.setCategory(Category.MOVEMENT);
		subscribe(PreMotionUpdateEvent.class, event -> {
			if (shouldGlide()) {
				getPlayer().setVelocity(getPlayer().getVelocity().setY(-0.0315F));
				getPlayer().setJumpMovementFactor(getPlayer().getJumpMovementFactor() * 1.21337F);
			}
		});
	}

	private boolean shouldGlide() {
		return getPlayer().getVelocity().getY() <= -0.0315F && !getPlayer().isOnGround() &&
				!getPlayer().isInFluid(Fluid.WATER) && !getPlayer().isClimbing() &&
				!getPlayer().isInFluid(Fluid.LAVA) && !getPlayer().isCollided(Entity.CollisionType.VERTICAL);
	}

}
