package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;

/**
 * Basic label renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class LabelRenderer implements ComponentRenderer<Label> {

    private final FontRenderer fontRenderer;

    public LabelRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    @Override
    public void pre(Label label) {

    }

    @Override
    public void render(Label label) {
        fontRenderer.drawString(label.getFont(), label.getText(), label.getX(), label.getY(), label.getColor().getRGB());
    }

    @Override
    public void post(Label label) {

    }
}
