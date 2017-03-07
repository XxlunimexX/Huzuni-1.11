package net.halalaboos.huzuni.indev.gui.impl.work;

import net.halalaboos.huzuni.indev.gui.impl.BasicWorkstation;

import java.awt.*;

/**
 * Creates and cashes color objects based on name. <br>
 * Created by Brandon Williams on 3/6/2017.
 */
public class ColorPalette extends BasicWorkstation<Color> {

    @Override
    protected Color createObject(String name, Object... params) {
        if (params.length == 1 && params[0] instanceof Integer) {
            return new Color((int) params[0]);
        } else if (params.length == 4 && params[0] instanceof Integer && params[1] instanceof Integer && params[2] instanceof Integer && params[3] instanceof Integer) {
            return new Color((int) params[0], (int) params[1], (int) params[2], (int) params[3]);
        }
        return null;
    }
}
