package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.huzuni.mc.Reflection;

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
    	float speed = this.speed.getValue();
        if (Reflection.getRightClickDelayTimer() > (4 - (byte) speed))
            Reflection.setRightClickDelayTimer((4 - (byte) speed));
    }

}
