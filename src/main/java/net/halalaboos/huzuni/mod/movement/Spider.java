package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

/**
 * Allows the player to scale up blocks like a spider.
 * */
public class Spider extends BasicMod {
	
	public Spider() {
		super("Spider", "Climb up walls like a lil spider");
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
		ClientPlayer player = MCWrapper.getPlayer();
		if (player.isCollided(Entity.CollisionType.HORIZONTAL)) {
			Vector3d vel = player.getVelocity();
			player.setVelocity(new Vector3d(vel.x, 0.2D, vel.z));
			player.setOnGround(true);
		}
	}
}
