package net.halalaboos.huzuni.indev.gui;

/**
 * Simple utility class used within the components system to perform simple calculations. <br/>
 * Created by Brandon Williams on 2/4/2017.
 */
public interface Toolbox {

    /**
     * @return The x position of the mouse.
     * */
    int getMouseX();

    /**
     * @return The y position of the mouse.
     * */
    int getMouseY();

    /**
     * @return The width of the screen.
     * */
    int getWidth();

    /**
     * @return The height of the screen.
     * */
    int getHeight();

    /**
     * @return The user's clipboard string.
     * */
    String getClipboard();

    /**
     * @return True if the x and y positions are within the rectangle specified by the int array in the form: x, y, width, height.
     * */
    boolean isPointInside(int x, int y, int[] rect);

    /**
     * @return A workstation to create objects.
     * */
    Workstation getWorkstation();

}
