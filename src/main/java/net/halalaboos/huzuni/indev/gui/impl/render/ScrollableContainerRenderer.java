package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;


/**
 * Basic scrollable container renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ScrollableContainerRenderer implements ComponentRenderer<ScrollableContainer> {

    private final BasicRenderer basicRenderer;

    private final Toolbox toolbox;

    public ScrollableContainerRenderer(BasicRenderer basicRenderer, Toolbox toolbox) {
        this.basicRenderer = basicRenderer;
        this.toolbox = toolbox;
    }

    @Override
    public void pre(ScrollableContainer container) {
        /*glEnable(GL_STENCIL_TEST);
        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
        glStencilMask(0xFF);
        glColorMask(false, false, false, false);
        glDepthMask(false);
        RenderUtils.drawRect(container.getRenderArea());
        glColorMask(true, true, true, true);
        glDepthMask(true);
        glStencilFunc(GL_EQUAL, 1, 0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
        //glColorMask(false, false, false, false);
        //glDepthMask(false);*/
        glEnable(GL_SCISSOR_TEST);
        GLManager.glScissor(container.getArea());
    }

    @Override
    public void render(ScrollableContainer container) {
        if (!container.getTag().equals("invisible-background")) {
            GLManager.glColor(basicRenderer.getPack().getBackground());
            RenderUtils.drawRect(container.getRenderArea());
        }
        if (container.getVerticalScrollbar().has()) {
            GLManager.glColor(basicRenderer.getPack().getDefaultComponent());
            RenderUtils.drawRect(container.getVerticalScrollbar().getArea());
            GLManager.glColor(RenderUtils.getColorWithAffects(basicRenderer.getPack().getDefaultComponent().brighter(), container.getVerticalScrollbar().isScrolling() || (container.isHovered() && container.getVerticalScrollbar().isPointInsideBar(toolbox.getMouseX(), toolbox.getMouseY())), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(container.getVerticalScrollbar().getScrollbar());
        }
        if (container.getHorizontalScrollbar().has()) {
            GLManager.glColor(basicRenderer.getPack().getDefaultComponent());
            RenderUtils.drawRect(container.getHorizontalScrollbar().getArea());
            GLManager.glColor(RenderUtils.getColorWithAffects(basicRenderer.getPack().getDefaultComponent().brighter(), container.getHorizontalScrollbar().isScrolling() || (container.isHovered() && container.getHorizontalScrollbar().isPointInsideBar(toolbox.getMouseX(), toolbox.getMouseY())), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(container.getHorizontalScrollbar().getScrollbar());
        }
    }

    @Override
    public void post(ScrollableContainer container) {
        glDisable(GL_SCISSOR_TEST);
        //glDisable(GL_STENCIL_TEST);
    }
}
