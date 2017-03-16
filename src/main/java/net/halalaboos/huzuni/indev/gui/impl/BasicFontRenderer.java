package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.FontData;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;

/**
 * Basic implementation of the FontRenderer interface.
 * */
public class BasicFontRenderer implements FontRenderer {

    protected int kerning = 0;

    protected FontData fontData = new FontData();

    public BasicFontRenderer() {}

	@Override
	public int drawString(FontData fontData, String text, int x, int y, int color) {
        if (!fontData.hasFont())
            return 0;
        getGLStateManager().pushMatrix();
		getGLStateManager().scale(0.5F, 0.5F, 0.5F);
        x *= 2;
        y *= 2;
		getGLStateManager().enableBlend();
		fontData.bind();
        GLUtils.glColor(color);
		int size = text.length();
		for (int i = 0; i < size; i++) {
			char character = text.charAt(i);
			if (fontData.hasBounds(character)) {
                FontData.CharacterData area = fontData.getCharacterBounds(character);
                GLUtils.drawTextureRect(x, y, area.width, area.height,
                        (float) area.x / fontData.getImage().getWidth(),
                        (float) area.y / fontData.getImage().getHeight(),
                        (float) (area.x + area.width) / fontData.getImage().getWidth(),
                        (float) (area.y + area.height) / fontData.getImage().getHeight());
				x += (area.width + kerning);
			}
		}
		getGLStateManager().popMatrix();
		return x;
	}

	@Override
	public int drawString(String text, int x, int y, int color) {
        return drawString(fontData, text, x, y, color);
    }

    public int getKerning() {
        return kerning;
    }

    public void setKerning(int kerning) {
        this.kerning = kerning;
    }

    @Override
    public FontData getFontData() {
        return fontData;
    }

    @Override
    public void setFontData(FontData fontData) {
        this.fontData = fontData;
    }
}
