package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.render.PopupRenderer;

/**
 * Created by Brandon Williams on 2/24/2017.
 */
public class BasicPopupRenderer implements PopupRenderer {

    private BasicRenderer renderer;

    private final FontData tooltipFont;

    public BasicPopupRenderer(FontData tooltipFont) {
        this.tooltipFont = tooltipFont;
    }

    @Override
    public void drawTooltip(String tooltip, int x, int y) {
        if (tooltip != null) {
            GLManager.glColor(renderer.getPack().getDefaultComponent());
            RenderUtils.drawRect(x - 2, y - tooltipFont.getFontHeight() - 2, x + tooltipFont.getStringWidth(tooltip) + 2, y);
            renderer.getFontRenderer().drawString(tooltipFont, tooltip, x, y, 0xFFFFFFFF);
        }
    }

    protected void setRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }
}
