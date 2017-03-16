package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;

/**
 * Implementation of the component renderer which simply forces the constructor to accept a toolbox. <br/>
 * Created by Brandon Williams on 3/15/2017.
 */
public abstract class BasicComponentRenderer <T extends Component> implements ComponentRenderer<T> {

    protected final Toolbox toolbox;

    protected final FontRenderer fontRenderer;

    protected final ImageRenderer imageRenderer;

    public BasicComponentRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        this.toolbox = toolbox;
        this.fontRenderer = fontRenderer;
        this.imageRenderer = imageRenderer;
    }

    @Override
    public void pre(T component) {

    }

    @Override
    public void post(T component) {

    }
}
