package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;


/**
 * Basic container renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ContainerRenderer implements ComponentRenderer<Container> {

    private final BasicRenderer renderer;

    public ContainerRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void pre(Container container) {

    }

    @Override
    public void render(Container container) {
        if (!container.getTag().equals("invisible")) {
            GLManager.glColor(renderer.getPack().getSecondaryBackground());
            RenderUtils.drawRect(container.getArea());
        }
    }

    @Override
    public void post(Container container) {

    }
}
