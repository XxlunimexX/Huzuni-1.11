package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.render.GLManager;
import net.halalaboos.huzuni.api.util.render.RenderUtils;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;


/**
 * Basic scrollable container renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ScrollableContainerRenderer implements ComponentRenderer<ScrollableContainer> {

    private final BasicRenderer basicRenderer;

    public ScrollableContainerRenderer(BasicRenderer basicRenderer) {
        this.basicRenderer = basicRenderer;
    }

    @Override
    public void pre(ScrollableContainer container) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GLManager.glScissor(container.getArea());
    }

    @Override
    public void render(ScrollableContainer container) {
        if (!container.getTag().equals("invisible-BACKGROUND")) {
            GLManager.glColor(BasicRenderer.BACKGROUND);
            RenderUtils.drawRect(container.getRenderArea());
        }
        if (container.getVerticalScrollbar().has()) {
            GLManager.glColor(BasicRenderer.GREY);
            RenderUtils.drawRect(container.getVerticalScrollbar().getArea());
            GLManager.glColor(RenderUtils.getColorWithAffects(BasicRenderer.SCROLLBAR, container.getVerticalScrollbar().isScrolling() || (container.isHovered() && container.getVerticalScrollbar().isPointInsideBar(basicRenderer.getMouseX(), basicRenderer.getMouseY())), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(container.getVerticalScrollbar().getScrollbar());
        }
        if (container.getHorizontalScrollbar().has()) {
            GLManager.glColor(BasicRenderer.GREY);
            RenderUtils.drawRect(container.getHorizontalScrollbar().getArea());
            GLManager.glColor(RenderUtils.getColorWithAffects(BasicRenderer.SCROLLBAR, container.getHorizontalScrollbar().isScrolling() || (container.isHovered() && container.getHorizontalScrollbar().isPointInsideBar(basicRenderer.getMouseX(), basicRenderer.getMouseY())), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(container.getHorizontalScrollbar().getScrollbar());
        }
    }

    @Override
    public void post(ScrollableContainer container) {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
