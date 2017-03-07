package net.halalaboos.huzuni.indev.gui;

/**
 * Created by Brandon Williams on 3/7/2017.
 */
public interface Image {

    /**
     * Binds to this image, allowing for rendering.
     * */
    void bind();

    /**
     * @return The id lwjgl created for representing this image.
     * */
    int getId();

    /**
     * @return This image's width.
     * */
    int getWidth();

    /**
     * @return This image's height.
     * */
    int getHeight();

}
