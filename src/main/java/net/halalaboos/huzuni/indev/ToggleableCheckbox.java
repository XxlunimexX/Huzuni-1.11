package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.settings.Toggleable;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;

/**
 * Checkbox implementation which utilizes a toggleable node. <br/>
 * Created by Brandon Williams on 2/16/2017.
 */
public class ToggleableCheckbox extends Checkbox {

    private final Toggleable toggleable;

    public ToggleableCheckbox(Toggleable toggleable) {
        super("toggleable", toggleable.getName(), toggleable.isEnabled());
        this.toggleable = toggleable;
    }

    @Override
    protected void onPressed() {
        super.onPressed();
        toggleable.toggle();
    }

    @Override
    public boolean isEnabled() {
        return toggleable.isEnabled();
    }
}
