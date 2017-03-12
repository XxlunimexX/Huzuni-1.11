package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.event.player.PostMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Allows the player to scale up blocks like a spider.
 * */
public class Spider extends BasicMod {
	
	public Spider() {
		super("Spider", "Climb up walls like a lil spider");
		setAuthor("Halalaboos");
		this.setCategory(Category.MOVEMENT);
		subscribe(PostMotionUpdateEvent.class, event -> {
			if (getPlayer().isCollided(Entity.CollisionType.HORIZONTAL)) {
				Vector3d vel = getPlayer().getVelocity();
				getPlayer().setVelocity(vel.addY(0.2));
				getPlayer().setOnGround(true);
			}
		});
	}
}
