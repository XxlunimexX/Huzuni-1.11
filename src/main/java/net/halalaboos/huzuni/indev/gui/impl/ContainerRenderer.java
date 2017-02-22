package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;


/**
 * Basic container renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ContainerRenderer implements ComponentRenderer<Container> {

    @Override
    public void pre(Container container) {

    }

    @Override
    public void render(Container container) {
        if (!container.getTag().equals("invisible")) {
            GLManager.glColor(BasicRenderer.SECONDARY_BACKGROUND);
            RenderUtils.drawRect(container.getArea());
        }
    }

    @Override
    public void post(Container container) {

    }
}
