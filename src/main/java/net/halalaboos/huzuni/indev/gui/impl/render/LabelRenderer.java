package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.impl.BasicComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;

/**
 * Basic label renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class LabelRenderer extends BasicComponentRenderer<Label> {

    public LabelRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        super(toolbox, fontRenderer, imageRenderer);
    }

    @Override
    public void render(Label label) {
        fontRenderer.drawString(label.getFont(), label.getText(), label.getX(), label.getY(), label.getColor().getRGB());
    }
}
