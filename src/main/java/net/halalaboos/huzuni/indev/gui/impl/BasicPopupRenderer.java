package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;
import net.halalaboos.huzuni.indev.gui.render.PopupRenderer;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;

/**
 * Basic popup renderer. <br/>
 * Created by Brandon Williams on 2/24/2017.
 */
public class BasicPopupRenderer implements PopupRenderer {

    private final Toolbox toolbox;

    private final FontRenderer fontRenderer;

    private final ImageRenderer imageRenderer;

    public BasicPopupRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        this.toolbox = toolbox;
        this.fontRenderer = fontRenderer;
        this.imageRenderer = imageRenderer;
    }

    @Override
    public void drawTooltip(String tooltip, int x, int y) {
        if (tooltip != null) {
            FontData font = toolbox.get(FONT_TOOLTIP);
            GLUtils.glColor(toolbox.get(COLOR_DEFAULT));
            GLUtils.drawRect(x - 2, y - font.getFontHeight() - 2, x + font.getStringWidth(tooltip) + 2, y);
            fontRenderer.drawString(font, tooltip, x, y - font.getFontHeight() - 1, 0xFFFFFFFF);
        }
    }
}
