package net.halalaboos.huzuni.indev.script;

import net.halalaboos.huzuni.api.gui.font.BasicFontRenderer;
import net.halalaboos.huzuni.api.gui.font.FontManager;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;

import java.awt.*;

/**
 * Wrapper rendering class used by scripts. <br/>
 * Created by Brandon Williams on 2/18/2017.
 */
public final class ScriptGl {

    private final FontManager fontManager;

    private final FontRenderer fontRenderer = new BasicFontRenderer();

    protected ScriptGl(FontManager fontManager) {
        this.fontManager = fontManager;
    }

    /**
     * @return The width in pixels of the text rendered.
     * */
    public int drawString(String text, int x, int y, int color) {
        return fontRenderer.drawString(text, x, y, color);
    }

    /**
     * Renders a string with a black shadow behind. <br/>
     * @return The width in pixels of the text rendered.
     * */
    public int drawStringWithShadow(String text, int x, int y, int color) {
        return Math.max(fontRenderer.drawString(text, x, y, color), fontRenderer.drawString(text, x + 1, y + 1, 0xFF000000));
    }

    /**
     * Sets the font used by this font renderer to a plain font with the size and name specified.
     * */
    public void setFont(String fontName, int fontSize) {
        this.fontRenderer.setFontData(fontManager.getFont(fontName, fontSize, Font.PLAIN, true));
    }

    /**
     * Set the font used by this font renderer to a font which matches the criteria specified.
     * */
    public void setFont(String fontName, int fontSize, int style) {
        this.fontRenderer.setFontData(fontManager.getFont(fontName, fontSize, style, true));
    }

}
