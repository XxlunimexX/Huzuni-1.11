package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.impl.BasicComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;
import static org.lwjgl.opengl.GL11.*;


/**
 * Basic scrollable container renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ScrollableContainerRenderer extends BasicComponentRenderer<ScrollableContainer> {

    public ScrollableContainerRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        super(toolbox, fontRenderer, imageRenderer);
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
        GLUtils.glScissor(container.getArea());
    }

    @Override
    public void render(ScrollableContainer container) {
        if (!container.getTag().equals("invisible-background")) {
            GLUtils.glColor(toolbox.get(COLOR_BACKGROUND));
            GLUtils.drawRect(container.getRenderArea());
        }
        if (container.getVerticalScrollbar().has()) {
            GLUtils.glColor(toolbox.get(COLOR_DEFAULT));
            GLUtils.drawRect(container.getVerticalScrollbar().getArea());
            GLUtils.glColor(GLUtils.getColorWithAffects(toolbox.<Color>get(COLOR_DEFAULT).brighter(), container.getVerticalScrollbar().isScrolling() || (container.isHovered() && container.getVerticalScrollbar().isPointInsideBar(toolbox.getMouseX(), toolbox.getMouseY())), Mouse.isButtonDown(0)));
            GLUtils.drawRect(container.getVerticalScrollbar().getScrollbar());
        }
        if (container.getHorizontalScrollbar().has()) {
            GLUtils.glColor(toolbox.get(COLOR_DEFAULT));
            GLUtils.drawRect(container.getHorizontalScrollbar().getArea());
            GLUtils.glColor(GLUtils.getColorWithAffects(toolbox.<Color>get(COLOR_DEFAULT).brighter(), container.getHorizontalScrollbar().isScrolling() || (container.isHovered() && container.getHorizontalScrollbar().isPointInsideBar(toolbox.getMouseX(), toolbox.getMouseY())), Mouse.isButtonDown(0)));
            GLUtils.drawRect(container.getHorizontalScrollbar().getScrollbar());
        }
    }

    @Override
    public void post(ScrollableContainer container) {
        glDisable(GL_SCISSOR_TEST);
        //glDisable(GL_STENCIL_TEST);
    }
}
