package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.indev.ColorPack;
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
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;
import net.halalaboos.huzuni.indev.gui.render.RenderManager;

/**
 * Basic implementation of the renderer for the GUI. <br/>
 * Also implements the Toolbox interface, making it work for both parameters of a ContainerManager. <br/>
 * Created by Brandon Williams on 2/13/2017.
 */
public class BasicRenderer extends RenderManager {

    private final FontRenderer fontRenderer = new BasicFontRenderer();
    
    private final ImageRenderer imageRenderer = new BasicImageRenderer();

    private final Toolbox toolbox;

    public BasicRenderer(Toolbox toolbox) {
        super();
        this.toolbox = toolbox;
        this.setPopupRenderer(new BasicPopupRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(Button.class, new ButtonRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(Checkbox.class, new CheckboxRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(Container.class, new ContainerRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(ScrollableContainer.class, false, new ScrollableContainerRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(Slider.class, new SliderRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(Label.class, new LabelRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(TextField.class, new TextFieldRenderer(toolbox, fontRenderer, imageRenderer));
        this.setRenderer(ComboBox.class, new ComboBoxRenderer(toolbox, fontRenderer, imageRenderer));
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public ImageRenderer getImageRenderer() {
        return imageRenderer;
    }

    public Toolbox getToolbox() {
        return toolbox;
    }
}
