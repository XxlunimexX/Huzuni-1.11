package net.halalaboos.huzuni.indev.gui;

/**
 * Contains an x, y, width, and height. <br/>
 * Created by Brandon Williams on 2/28/2017.
 */
public interface Dimension {

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    /**
     * Width and height will be adjusted by the scale factor provided.
     * */
    void scale(float scale);

    /**
     * @return True if the given x and y position is within this dimension.
     * */
    boolean isPointInside(int x, int y);

    /**
     * @return An int array containing the x, y, width, and height in that order.
     * */
    int[] getArea();
}
