package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.render.PopupRenderer;

/**
 * Created by Brandon Williams on 2/24/2017.
 */
public class BasicPopupRenderer implements PopupRenderer {

    private BasicRenderer renderer;

    @Override
    public void drawTooltip(FontData fontData, String tooltip, int x, int y) {
        if (tooltip != null) {
            GLManager.glColor(renderer.getPalette().getDefaultComponent());
            RenderUtils.drawRect(x - 2, y - 2, x + fontData.getStringWidth(tooltip) + 2, y + fontData.getFontHeight());
            renderer.getFontRenderer().drawString(fontData, tooltip, x, y, 0xFFFFFFFF);
        }
    }

    protected void setRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }
}
