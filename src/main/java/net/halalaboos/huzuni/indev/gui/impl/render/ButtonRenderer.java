package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.impl.BasicComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;

/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ButtonRenderer extends BasicComponentRenderer<Button> {

    public ButtonRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        super(toolbox, fontRenderer, imageRenderer);
    }
    @Override
    public void render(Button button) {
        GLUtils.glColor(GLUtils.getColorWithAffects(button.isHighlight() ? toolbox.get(COLOR_HIGHLIGHT) : toolbox.get(COLOR_DEFAULT), button.isHovered(), Mouse.isButtonDown(0)));
        GLUtils.drawRect(button.getArea());
        int color = button.isHighlight() ? toolbox.<Color>get(COLOR_ENABLED_TEXT).getRGB() : toolbox.<Color>get(COLOR_DISABLED_TEXT).getRGB();
        drawString(button.getFont(), button.getText(), button.getX(), button.getY() + button.getHeight() / 2, color);
    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        fontRenderer.drawString(fontData, text.toUpperCase(), x + 4, y - fontData.getFontHeight() / 2, color);
    }
}
