package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.TypeCreator;
import org.apache.logging.log4j.Level;

import java.awt.*;

/**
 * Create a FontData object with the parameters in the order: font, type, size. <br/>
 * The parameters for the creation of each object are as follows: (String) params[0] = FONT, (int) params[1] = STYLE, (float) params[2] = SIZE <br/>
 * Created by Brandon Williams on 3/16/2017.
 */
public class FontCreator extends TypeCreator {
    @Override
    protected Object createObject(String name, Object... params) {
        FontData fontData = new FontData();
        if (params != null && params.length >= 3) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, FontCreator.class.getResourceAsStream(Huzuni.ASSETS_LOCATION + "fonts/" + params[0]));
                // Font font = Font.createFont(Font.TRUETYPE_FONT, FontCreator.class.getResourceAsStream(FilenameUtils.separatorsToSystem(FilenameUtils.normalize(Huzuni.ASSETS_LOCATION + "fonts/" + params[0]))));
                fontData = fontData.setFont(font.deriveFont((int) params[1], (int) params[2]), true);
            } catch (Exception e) {
                Huzuni.LOGGER.log(Level.FATAL, String.format("Unable to load font: '%s' Error: %s", params[0], e.getMessage()));
            }
        }
        return fontData;
    }
}
