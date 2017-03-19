package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.player.MoveEvent;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Prevents the player from falling from the edges of blocks.
 * */
public class Safewalk extends BasicMod {
	
	public Safewalk() {
		super("Safewalk", "It's like sneaking, but without the sneaking");
		setAuthor("Halalaboos");
		setCategory(Category.MOVEMENT);
		subscribe(MoveEvent.class, this::onPlayerMove);
	}

	private void onPlayerMove(MoveEvent event) {
		double x = event.getMotionX();
		double y = event.getMotionY();
		double z = event.getMotionZ();
		if (getPlayer().isOnGround()) {
			double increment;
			for (increment = 0.05D; x != 0.0D && isOffsetBBEmpty(x, -1.0D, 0.0D);) {
				if (x < increment && x >= -increment) {
					x = 0.0D;
				} else if (x > 0.0D) {
					x -= increment;
				} else {
					x += increment;
				}
			}
			for (; z != 0.0D && isOffsetBBEmpty(0.0D, -1.0D, z);) {
				if (z < increment && z >= -increment) {
					z = 0.0D;
				} else if (z > 0.0D) {
					z -= increment;
				} else {
					z += increment;
				}
			}
			for (; x != 0.0D && z != 0.0D && isOffsetBBEmpty(x, -1.0D, z);) {
				if (x < increment && x >= -increment) {
					x = 0.0D;
				} else if (x > 0.0D) {
					x -= increment;
				} else {
					x += increment;
				}
				if (z < increment && z >= -increment) {
					z = 0.0D;
				} else if (z > 0.0D) {
					z -= increment;
				} else {
					z += increment;
				}
			}
		}
		event.setMotionX(x);
		event.setMotionY(y);
		event.setMotionZ(z);
	}

	private boolean isOffsetBBEmpty(double x, double y, double z) {
		return getWorld().isOffsetBBEmpty(new Vector3d(x, y, z));
	}
}
