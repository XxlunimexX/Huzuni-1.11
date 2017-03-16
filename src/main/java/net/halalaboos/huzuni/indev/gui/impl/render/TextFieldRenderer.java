package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.impl.BasicComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;

/**
 * Basic textfield renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class TextFieldRenderer extends BasicComponentRenderer<TextField> {

    public TextFieldRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        super(toolbox, fontRenderer, imageRenderer);
    }

    @Override
    public void render(TextField textField) {
        if (textField.getTag().equals("lined")) {
            GLUtils.glColor(GLUtils.getColorWithAffects(textField.isTyping() ? toolbox.get(COLOR_HIGHLIGHT) : toolbox.<Color>get(COLOR_DEFAULT).brighter(), textField.isHovered(), Mouse.isButtonDown(0)));
            GLUtils.drawLine(1F, textField.getX(), textField.getY() + textField.getHeight() - 1F, textField.getX() + textField.getWidth(), textField.getY() + textField.getHeight() - 1F);
        } else {
            GLUtils.glColor(GLUtils.getColorWithAffects(textField.isTyping() ? toolbox.<Color>get(COLOR_DEFAULT).brighter() : toolbox.get(COLOR_DEFAULT), textField.isHovered(), Mouse.isButtonDown(0)));
            GLUtils.drawRect(textField.getArea());
        }
        if (textField.hasText()) {
            int color = textField.isTyping() ? textField.getColor().getRGB() : textField.getColor().darker().getRGB();
            drawString(textField.getFont(), textField.getRenderText(textField.isTyping()), textField.getX(), textField.getY() + textField.getHeight() / 2, color);
        } else {
            drawString(textField.getFont(), textField.getDefaultText(), textField.getX(), textField.getY() + textField.getHeight() / 2, toolbox.<Color>get(COLOR_DISABLED_TEXT).getRGB());
        }
    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        fontRenderer.drawString(fontData, text, x + 2, y - fontData.getFontHeight() / 2, color);
    }
}
