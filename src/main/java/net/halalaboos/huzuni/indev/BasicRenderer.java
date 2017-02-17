package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.util.render.GLManager;
import net.halalaboos.huzuni.api.util.render.RenderUtils;
import net.halalaboos.huzuni.indev.gui.*;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.Slider;
import net.halalaboos.huzuni.api.gui.font.BasicFontRenderer;
import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.RenderManager;
import org.lwjgl.input.Mouse;

import java.awt.Color;

/**
 * Basic implementation of the renderer for the GUI. <br/>
 * Also implements the InputUtility interface, making it work for both parameters of a ContainerManager.<br/>
 * Created by Brandon Williams on 2/13/2017.
 */
public class BasicRenderer extends RenderManager implements InputUtility {

    private final Color background = new Color(0, 0, 0, 255);
    private final Color secondaryBackground = new Color(60, 60, 60, 255);

    private final Color highlight = new Color(0, 127, 255, 255);
    private final Color grey = new Color(127, 127, 127, 255);

    private final FontRenderer fontRenderer = new BasicFontRenderer();

    public BasicRenderer() {
        super();
        this.setRenderer(Button.class, new ComponentRenderer<Button>() {
            @Override
            public void render(Button button) {
                GLManager.glColor(RenderUtils.getColorWithAffects(button.isHighlight() ? highlight : grey, button.isHovered(), Mouse.isButtonDown(0)));
                RenderUtils.drawRect(button.getArea());
                drawStringCentered(button.getFont(), button.getText(), button.getX() + button.getWidth() / 2, button.getY() + button.getHeight() / 2, 0xFFFFFFFF);
            }
        });

        this.setRenderer(Checkbox.class, new ComponentRenderer<Checkbox>() {
            @Override
            public void render(Checkbox checkbox) {
                GLManager.glColor(RenderUtils.getColorWithAffects(checkbox.isEnabled() ? highlight : grey, checkbox.isHovered(), Mouse.isButtonDown(0)));
                RenderUtils.drawRect(checkbox.getCheckbox());
                fontRenderer.drawString(checkbox.getFont(), checkbox.getText(), checkbox.getX() + 2 + Checkbox.CHECKBOX_SIZE, checkbox.getY(), 0xFFFFFFFF);
            }
        });

        this.setRenderer(Container.class, new ComponentRenderer<Container>() {
            @Override
            public void render(Container container) {
                GLManager.glColor(secondaryBackground);
                RenderUtils.drawRect(container.getArea());
            }
        });

        this.setRenderer(ScrollableContainer.class, false, new ComponentRenderer<ScrollableContainer>() {
            @Override
            public void render(ScrollableContainer container) {
                GLManager.glColor(background);
                RenderUtils.drawRect(container.getRenderArea());
                if (container.getVerticalScrollbar().has()) {
                    GLManager.glColor(grey);
                    RenderUtils.drawRect(container.getVerticalScrollbar().getArea());
                    GLManager.glColor(RenderUtils.getColorWithAffects(highlight, container.getVerticalScrollbar().isScrolling() || (container.isHovered() && container.getVerticalScrollbar().isPointInsideBar(getMouseX(), getMouseY())), Mouse.isButtonDown(0)));
                    RenderUtils.drawRect(container.getVerticalScrollbar().getScrollbar());
                }
            }
        });

        this.setRenderer(Slider.class, new ComponentRenderer<Slider>() {
            @Override
            public void render(Slider slider) {
                GLManager.glColor(grey);
                RenderUtils.drawRect(slider.getArea());
                GLManager.glColor(RenderUtils.getColorWithAffects(highlight, slider.isSliding() || slider.isHovered(), Mouse.isButtonDown(0)));
                RenderUtils.drawRect(slider.getSliderBar());
            }
        });

        this.setRenderer(Label.class, new ComponentRenderer<Label>() {
            @Override
            public void render(Label label) {
                fontRenderer.drawString(label.getFont(), label.getText(), label.getX(), label.getY(), label.getColor().getRGB());
            }
        });

    }

    /**
     * Draws a centered string with the default font renderer.
     * */
    private void drawStringCentered(FontData fontData, String text, int x, int y, int color) {
        fontRenderer.drawString(fontData, text, x - fontData.getStringWidth(text) / 2, y - fontData.getFontHeight() / 2, color);
    }

    @Override
    public int getMouseX() {
        return GLManager.getMouseX();
    }

    @Override
    public int getMouseY() {
        return GLManager.getMouseY();
    }

    @Override
    public int getWidth() {
        return GLManager.getScreenWidth();
    }

    @Override
    public int getHeight() {
        return GLManager.getScreenHeight();
    }

    @Override
    public boolean isPointInside(int x, int y, int[] rect) {
        return x > rect[0] && y > rect[1] && x < rect[0] + rect[2] && y < rect[1] + rect[3];
    }
}
