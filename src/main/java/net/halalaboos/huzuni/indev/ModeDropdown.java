package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.Nameable;
import net.halalaboos.huzuni.indev.gui.components.Dropdown;

/**
 * Created by Brandon Williams on 3/5/2017.
 */
public class ModeDropdown <I extends Nameable> extends Dropdown<I> {

    private final Mode<I> mode;

    public ModeDropdown(Mode<I> mode, FontData font) {
        super("mode", mode.getItems());
        this.mode = mode;
        this.setFont(font);
        this.setWidth(150);
        this.setSelected(mode.getSelected());
    }

    @Override
    public void setSelected(int selected) {
        super.setSelected(selected);
        mode.setSelectedItem(selected);
    }

    public Mode<I> getMode() {
        return mode;
    }
}
