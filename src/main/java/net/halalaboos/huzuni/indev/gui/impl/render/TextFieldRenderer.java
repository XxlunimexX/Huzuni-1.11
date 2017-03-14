package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.impl.ColorPack;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

/**
 * Basic textfield renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class TextFieldRenderer implements ComponentRenderer<TextField> {

    private final BasicRenderer renderer;

    public TextFieldRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void pre(TextField textField) {

    }

    @Override
    public void render(TextField textField) {
        ColorPack pack = renderer.getPack();
        if (textField.getTag().equals("lined")) {
            GLManager.glColor(RenderUtils.getColorWithAffects(textField.isTyping() ? pack.getHighlightComponent() : pack.getDefaultComponent().brighter(), textField.isHovered(), Mouse.isButtonDown(0)));
            RenderUtils.drawLine(1F, textField.getX(), textField.getY() + textField.getHeight() - 1F, textField.getX() + textField.getWidth(), textField.getY() + textField.getHeight() - 1F);
        } else {
            GLManager.glColor(RenderUtils.getColorWithAffects(textField.isTyping() ? pack.getDefaultComponent().brighter() : pack.getDefaultComponent(), textField.isHovered(), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(textField.getArea());
        }
        if (textField.hasText()) {
            int color = textField.isTyping() ? textField.getColor().getRGB() : textField.getColor().darker().getRGB();
            drawString(textField.getFont(), textField.getRenderText(textField.isTyping()), textField.getX(), textField.getY() + textField.getHeight() / 2, color);
        } else {
            drawString(textField.getFont(), textField.getDefaultText(), textField.getX(), textField.getY() + textField.getHeight() / 2, pack.getDisabledText().getRGB());
        }
    }

    @Override
    public void post(TextField textField) {

    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        renderer.getFontRenderer().drawString(fontData, text, x + 2, y - fontData.getFontHeight() / 2, color);
    }
}
