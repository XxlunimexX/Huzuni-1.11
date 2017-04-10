package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.node.attribute.Nameable;

/**
 * Pointers used by the toolbox. <br/>
 * Created by Brandon Williams on 3/15/2017.
 */
public enum Pointers implements Nameable {
    COLOR_HIGHLIGHT("color_highlight", "Highlight color used within the GUI."),
    COLOR_BACKGROUND("color_background", "Background color used within the GUI."),
    COLOR_SECONDARY_BACKGROUND("color_secondary_background", "Secondary background color used within the GUI."),
    COLOR_DEFAULT("color_default", "Default component color used within the GUI."),
    COLOR_ENABLED_TEXT("color_enabled_text", "Color used for enabled text within the GUI"),
    COLOR_DISABLED_TEXT("color_disabled_text", "Color used for disabled text within the GUI."),
    FONT_TOOLTIP("font_tooltip", "Font used within tooltips."),
    IMAGE_ARROW("image_arrow", "Image used for the arrow of the combo box.");

    private final String name, description;

    Pointers(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
