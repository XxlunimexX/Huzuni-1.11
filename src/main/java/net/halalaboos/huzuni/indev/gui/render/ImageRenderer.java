package net.halalaboos.huzuni.indev.gui.render;

import net.halalaboos.huzuni.indev.gui.ImageData;

/**
 * Re
 * Created by Brandon Williams on 3/7/2017.
 */
public interface ImageRenderer {

    /**
     * Draws the given image at the x and y positions with the given width and height.
     * */
    void draw(ImageData image, int x, int y, int width, int height);

    /**
     * Draws the given image at the x and y coordinates scaled by the scale factor provided.
     * */
    void draw(ImageData image, int x, int y, float scale);

}
