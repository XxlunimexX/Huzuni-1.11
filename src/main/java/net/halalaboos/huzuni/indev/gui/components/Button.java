package net.halalaboos.huzuni.indev.gui.components;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;

/**
 * Simple button implementation. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class Button extends Component {

    private String text;

    private boolean highlight;

    private boolean pressed = false;

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
            if (pressed && isPointInside(action.x, action.y)) {
                pressed = false;
                onPressed();
                return true;
            }
            pressed = false;
            return false;
        });
    }

    @Override
    public void update() {
    }

    protected void onPressed() {}

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
