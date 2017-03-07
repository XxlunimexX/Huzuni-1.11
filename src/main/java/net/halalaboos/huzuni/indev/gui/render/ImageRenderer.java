package net.halalaboos.huzuni.indev.gui.render;

import net.halalaboos.huzuni.indev.gui.Image;

/**
 * Created by Brandon Williams on 3/7/2017.
 */
public interface ImageRenderer {

    void draw(Image image, int x, int y, int width, int height);

    void draw(Image image, int x, int y, float scale);

}
