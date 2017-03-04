package net.halalaboos.huzuni.mod.combat;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.MCWrapper;

/**
 * Created by Brandon Williams on 2/28/2017.
 */
public class Autoeat extends BasicMod {

    public Autoeat() {
        super("Auto eat", "");
        setCategory(Category.COMBAT);
        setAuthor("Halalaboos");
    }

    @Override
    protected void onEnable() {
        huzuni.eventManager.addListener(this);
    }

    @Override
    protected void onDisable() {
        huzuni.eventManager.addListener(this);
    }

    @EventMethod
    public void onUpdate(UpdateEvent event) {
    }
}
