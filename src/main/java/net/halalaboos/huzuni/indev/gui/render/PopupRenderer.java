package net.halalaboos.huzuni.indev.gui.render;

import net.halalaboos.huzuni.api.gui.font.FontData;

/**
 * Created by Brandon Williams on 2/24/2017.
 */
public interface PopupRenderer {

    void drawTooltip(FontData fontData, String tooltip, int x, int y);

}
