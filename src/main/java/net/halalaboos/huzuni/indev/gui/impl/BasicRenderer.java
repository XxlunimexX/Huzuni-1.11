package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.indev.gui.*;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.components.*;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.impl.render.*;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.RenderManager;

/**
 * Basic implementation of the renderer for the GUI. <br/>
 * Also implements the Toolbox interface, making it work for both parameters of a ContainerManager. <br/>
 * Created by Brandon Williams on 2/13/2017.
 */
public class BasicRenderer extends RenderManager {

    private final FontRenderer fontRenderer = new BasicFontRenderer();

    private ColorPack pack = ColorPack.values()[(int) (Math.random() * ColorPack.values().length)];

    public BasicRenderer(FontData popupFont, Toolbox toolbox) {
        super(new BasicPopupRenderer(popupFont));
        ((BasicPopupRenderer) getPopupRenderer()).setRenderer(this);
        this.setRenderer(Button.class, new ButtonRenderer(this));
        this.setRenderer(Checkbox.class, new CheckboxRenderer(this));
        this.setRenderer(Container.class, new ContainerRenderer(this));
        this.setRenderer(ScrollableContainer.class, false, new ScrollableContainerRenderer(this, toolbox));
        this.setRenderer(Slider.class, new SliderRenderer(this));
        this.setRenderer(Label.class, new LabelRenderer(fontRenderer));
        this.setRenderer(TextField.class, new TextFieldRenderer(this));
        this.setRenderer(ComboBox.class, new ComboBoxRenderer(this, toolbox));
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public ColorPack getPack() {
        return pack;
    }

    public void setPack(ColorPack pack) {
        this.pack = pack;
    }
}
