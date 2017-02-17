package net.halalaboos.huzuni.indev.gui.actions;

/**
 * Handles actions of components. Essentially an event listener for a specified action which is applied onto a component. <br/>
 * Created by Brandon Williams on 2/10/2017.
 */
public interface ActionListener<O> {

    /**
     * @return True if the action activated the component (cancelling this action for every subsequent component).
     * */
    boolean onAction(O object);
}
