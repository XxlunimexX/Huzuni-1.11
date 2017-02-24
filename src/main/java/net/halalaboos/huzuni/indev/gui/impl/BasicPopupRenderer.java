package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.BasicFontRenderer;
import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.render.PopupRenderer;

/**
 * Created by Brandon Williams on 2/24/2017.
 */
public class BasicPopupRenderer implements PopupRenderer {

    public final FontRenderer fontRenderer = new BasicFontRenderer();

    @Override
    public void drawTooltip(FontData fontData, String tooltip, int x, int y) {
        if (tooltip != null) {
            GLManager.glColor(BasicRenderer.GREY);
            RenderUtils.drawRect(x - 2, y - 2, x + fontData.getStringWidth(tooltip) + 2, y + fontData.getFontHeight());
            fontRenderer.drawString(fontData, tooltip, x, y, 0xFFFFFFFF);
        }
    }
}
