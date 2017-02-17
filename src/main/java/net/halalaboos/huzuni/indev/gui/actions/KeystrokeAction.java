package net.halalaboos.huzuni.indev.gui.actions;

/**
 * Keystroke action is passed to components when a key is pressed and released.<br/>
 * Created by Brandon Williams on 2/10/2017.
 */
public class KeystrokeAction {

    public final int key;

    public final char characater;

    public KeystrokeAction(int key, char characater) {
        this.key = key;
        this.characater = characater;
    }

    /**
     * Listener for the keystroke action.
     * */
    public interface KeystrokeActionListener extends ActionListener<KeystrokeAction> {

    }
}
