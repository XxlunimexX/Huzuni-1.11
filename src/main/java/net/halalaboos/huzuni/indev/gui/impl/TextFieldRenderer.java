package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

/**
 * Basic textfield renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class TextFieldRenderer implements ComponentRenderer<TextField> {

    private final Color typing = new Color(255, 255, 255, 255);

    private final Color notTyping = new Color(138, 138, 138, 255);

    private final FontRenderer fontRenderer;

    public TextFieldRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    @Override
    public void pre(TextField textField) {

    }

    @Override
    public void render(TextField textField) {
        if (textField.getTag().equals("lined")) {
            GLManager.glColor(RenderUtils.getColorWithAffects(textField.isTyping() ? BasicRenderer.ENABLED : BasicRenderer.GREY, textField.isHovered(), Mouse.isButtonDown(0)));
            RenderUtils.drawLine(1F, textField.getX(), textField.getY() + textField.getHeight() - 1F, textField.getX() + textField.getWidth(), textField.getY() + textField.getHeight() - 1F);
        } else {
            GLManager.glColor(RenderUtils.getColorWithAffects(textField.isTyping() ? BasicRenderer.ENABLED : BasicRenderer.GREY, textField.isHovered(), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(textField.getArea());
        }
        if (textField.hasText()) {
            int color = textField.isTyping() ? typing.getRGB() : notTyping.getRGB();
            drawString(textField.getFont(), textField.getRenderText(textField.isTyping()), textField.getX(), textField.getY() + textField.getHeight() / 2, color);
        } else {
            drawString(textField.getFont(), textField.getDefaultText(), textField.getX(), textField.getY() + textField.getHeight() / 2, new Color(108, 108, 108, 255).getRGB());
        }
    }

    @Override
    public void post(TextField textField) {

    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        fontRenderer.drawString(fontData, text.toUpperCase(), x + 2, y - fontData.getFontHeight() / 2, color);
    }
}
