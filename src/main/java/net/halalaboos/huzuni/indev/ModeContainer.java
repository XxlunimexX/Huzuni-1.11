package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.layouts.GridLayout;

/**
 * Mode container. <br/>
 * Created by Brandon Williams on 3/16/2017.
 */
public class ModeContainer extends Container {

    private final Mode mode;

    public ModeContainer(Mode mode, FontData font) {
        super("invisible");
        this.mode = mode;
        this.setLayout(new GridLayout(GridLayout.INFINITE_LENGTH, 2, 0, 1,1));
        this.setUseLayoutSize(true);
        this.setAutoLayout(true);
        Label title = new Label("title", mode.getName());
        title.setFont(font);
        title.setTooltip(mode.getDescription());
        this.add(title);
        this.add(new ModeComboBox<>(mode, font));
        this.layout();
    }

    public Mode getMode() {
        return mode;
    }
}
