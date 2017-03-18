package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.TypeCreator;

import java.awt.*;

/**
 * Create a FontData object with the parameters in the order: font, type, size. <br/>
 * Created by Brandon Williams on 3/16/2017.
 */
public class FontCreator extends TypeCreator {
    @Override
    protected Object createObject(String name, Object... params) {
        FontData font = new FontData();
        if (params.length >= 3) {
            // FONT, STYLE, SIZE
            font = font.setFont(new Font((String) params[0], (int) params[1], (int) params[2]), true);
        }
        return font;
    }
}
