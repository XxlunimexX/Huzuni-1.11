package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.indev.gui.Toolbox;

import java.awt.*;
import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;

/**
 * Color palettes used within the renderer. <br/>
 * Each palette contains a highlight which can be found within the material design guidelines. <br/>
 * Visit <a href="https://material.io/guidelines/style/color.html#color-color-palette">Here</a> to find out more. <br/>
 * Created by Brandon Williams on 3/6/2017.
 */
public enum ColorPack {

    DEFAULT(0xFF297E25),
    RED(0xFFF44336),
    PINK(0xFFE91E6),
    PURPLE(0xFF297E25),
    DEEP_PURPLE(0xFF673AB7),
    INDIGO(0xFF3F51B5),
    BLUE(0xFF2196F3),
    LIGHT_BLUE(0xFF03A9F4),
    CYAN(0xFF00BCD4),
    TEAL(0xFF009688),
    GREEN(0xFF4CAF50),
    LIGHT_GREEN(0xFF8BC34A),
    LIME(0xFFCDDC39),
    YELLOW(0xFFFFEB3B),
    AMBER(0xFFFFC107),
    ORANGE(0xFFFF9800),
    DEEP_ORANGE(0xFF5722),
    BROWN(0xFF795548),
    GREY(0xFF9E9E9E),
    BLUE_GREY(0xFF607D8B);

    private final Color background, secondaryBackground, defaultComponent, highlightComponent, enabledText, disabledText;

    ColorPack(int highlight) {
        this(0xFF141414, 0xFF232323, 0xFF2D2D2D, highlight, 0xFFFFFFFF, 0xFF8A8A8A);
    }

    ColorPack(int background, int secondaryBackground, int defaultComponent, int highlightComponent, int enabledText, int disabledText) {
        this.background = new Color(background);
        this.secondaryBackground = new Color(secondaryBackground);
        this.defaultComponent = new Color(defaultComponent);
        this.highlightComponent = new Color(highlightComponent);
        this.enabledText = new Color(enabledText);
        this.disabledText = new Color(disabledText);
    }

    /**
     * Applies this color pack to the toolbox provided.
     * */
    public void apply(Toolbox toolbox) {
        toolbox.put(COLOR_BACKGROUND, background);
        toolbox.put(COLOR_SECONDARY_BACKGROUND, secondaryBackground);
        toolbox.put(COLOR_DEFAULT, defaultComponent);
        toolbox.put(COLOR_HIGHLIGHT, highlightComponent);
        toolbox.put(COLOR_ENABLED_TEXT, enabledText);
        toolbox.put(COLOR_DISABLED_TEXT, disabledText);
    }

    public Color getBackground() {
        return background;
    }

    public Color getSecondaryBackground() {
        return secondaryBackground;
    }

    public Color getDefaultComponent() {
        return defaultComponent;
    }

    public Color getHighlightComponent() {
        return highlightComponent;
    }

    public Color getEnabledText() {
        return enabledText;
    }

    public Color getDisabledText() {
        return disabledText;
    }
}
