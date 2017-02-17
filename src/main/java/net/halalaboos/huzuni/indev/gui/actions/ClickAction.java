package net.halalaboos.huzuni.indev.gui.actions;

/**
 * This action occurs when a mouse button is pressed or released. <br/>
 * Created by Brandon Williams on 2/10/2017.
 */
public class ClickAction {

    /**
     * The mouse x position of this click.
     * */
    public final int x;

    /**
     * The mouse y position of this click.
     * */
    public final int y;

    /**
     * The id which represents the button pressed.
     * */
    public final int buttonId;

    public ClickAction(int x, int y, int buttonId) {
        this.x = x;
        this.y = y;
        this.buttonId = buttonId;
    }

    /**
     * Listener for the click action.
     * */
    public interface ClickActionListener extends ActionListener<ClickAction> {

    }
}
