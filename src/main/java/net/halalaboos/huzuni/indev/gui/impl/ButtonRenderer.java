package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.api.util.render.GLManager;
import net.halalaboos.huzuni.api.util.render.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ButtonRenderer implements ComponentRenderer<Button> {

    private final Color buttonOn = new Color(255, 255, 255, 255);

    private final Color buttonOff = new Color(138, 138, 138, 255);

    private final FontRenderer fontRenderer;

    public ButtonRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    @Override
    public void pre(Button button) {

    }

    @Override
    public void render(Button button) {
        GLManager.glColor(RenderUtils.getColorWithAffects(button.isHighlight() ? BasicRenderer.ENABLED : BasicRenderer.GREY, button.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(button.getArea());
        int color = button.isHighlight() ? buttonOn.getRGB() : buttonOff.getRGB();
        drawString(button.getFont(), button.getText(), button.getX(), button.getY() + button.getHeight() / 2, color);
    }

    @Override
    public void post(Button button) {

    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        fontRenderer.drawString(fontData, text.toUpperCase(), x + 4, y - fontData.getFontHeight() / 2, color);
    }
}
