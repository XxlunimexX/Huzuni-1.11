package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.event.player.PostMotionUpdateEvent;

/**
 * Allows the Player to place blocks at a faster rate by adjusting Minecraft's right click delay timer to the set
 * speed.
 *
 * <p>Since the right click delay timer is de-incremented every tick, we can't easily just set a 'max' value and be
 * done with it.  So instead, we check if the right click delay timer is greater than our set speed, and
 * if it is, then we set it to our set speed.</p>
 *
 * @author Halalaboos
 */
public class Fastplace extends BasicMod {

	/**
	 * The speed to place blocks, based on ticks.
	 */
	public final Value speed = new Value("Speed", 1F, 2F, 4F, 1F, "Speed you will place blocks at");
	
	public Fastplace() {
		super("Fast place", "Places blocks at a faster rate");
		this.setCategory(Category.MINING);
		this.addChildren(speed);
		setAuthor("Halalaboos");
		subscribe(PostMotionUpdateEvent.class, event -> {
			float speed = this.speed.getValue();
			if (mc.getRightClickDelayTimer() > (4 - (byte) speed))
				mc.setRightClickDelayTimer((4 - (byte) speed));
		});
	}

}
