package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ButtonRenderer implements ComponentRenderer<Button> {

    private final BasicRenderer renderer;

    public ButtonRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void pre(Button button) {

    }

    @Override
    public void render(Button button) {
        ColorPack palette = renderer.getPalette();
        GLManager.glColor(RenderUtils.getColorWithAffects(button.isHighlight() ? palette.getHighlightComponent() : palette.getDefaultComponent(), button.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(button.getArea());
        int color = button.isHighlight() ? palette.getEnabledText().getRGB() : palette.getDisabledText().getRGB();
        drawString(button.getFont(), button.getText(), button.getX(), button.getY() + button.getHeight() / 2, color);
    }

    @Override
    public void post(Button button) {

    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        renderer.getFontRenderer().drawString(fontData, text.toUpperCase(), x + 4, y - fontData.getFontHeight() / 2, color);
    }
}
