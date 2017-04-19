package net.halalaboos.huzuni.indev.gui.components;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;

import java.util.function.BiFunction;

/**
 * Simple button implementation. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class Button extends Component {

    private String text;

    private boolean highlight;

    private boolean pressed = false;

    private BiFunction<Button, ClickAction, Boolean> pressFunction;

    public Button(String tag, String text) {
        this(tag, text, false);
    }

    public Button(String tag, String text, boolean highlight) {
        super(tag);
        this.text = text;
        this.highlight = highlight;
        this.addListener(Actions.MOUSEPRESS, (ClickAction.ClickActionListener) action -> {
            if (isHovered() && isPointInside(action.x, action.y)) {
                pressed = true;
                return true;
            }
            return false;
        });
        this.addListener(Actions.MOUSERELEASE, (ClickAction.ClickActionListener) action -> {
            if (pressed && isPointInside(action.x, action.y) && pressFunction.apply(this, action)) {
                pressed = false;
                return true;
            }
            pressed = false;
            return false;
        });
    }

    @Override
    public void update() {
    }

    /**
     * Invokes the given function when this button is pressed.
     * */
    public void onPressed(BiFunction<Button, ClickAction, Boolean> pressFunction) {
        this.pressFunction = pressFunction;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public boolean isPressed() {
        return pressed;
    }
}
