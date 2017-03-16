package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.impl.BasicComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;


/**
 * Basic container renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ContainerRenderer extends BasicComponentRenderer<Container> {

    public ContainerRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        super(toolbox, fontRenderer, imageRenderer);
    }

    @Override
    public void render(Container container) {
        if (!container.getTag().equals("invisible")) {
            GLUtils.glColor(toolbox.get(COLOR_SECONDARY_BACKGROUND));
            GLUtils.drawRect(container.getArea());
        }
    }
}
