package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.mcwrapper.api.event.PostMotionUpdateEvent;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * Modifies players placement speed.
 * */
public class Fastplace extends BasicMod {
	
	public final Value speed = new Value("Speed", 1F, 2F, 4F, 1F, "Speed you will place blocks at");
	
	public Fastplace() {
		super("Fast place", "Places blocks at a faster rate");
		this.setCategory(Category.MINING);
		this.addChildren(speed);
		setAuthor("Halalaboos");
		subscribe(PostMotionUpdateEvent.class, event -> {
			float speed = this.speed.getValue();
			if (getMinecraft().getRightClickDelayTimer() > (4 - (byte) speed))
				getMinecraft().setRightClickDelayTimer((4 - (byte) speed));
		});
	}

}
