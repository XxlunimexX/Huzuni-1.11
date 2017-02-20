package net.halalaboos.huzuni.indev.gui.components;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;

/**
 * Simple checkbox implementation. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class Checkbox extends Component {

    public static final int CHECKBOX_SIZE = 12;

    private String text;

    private boolean enabled = false;

    private boolean pressed = false;

    public Checkbox(String tag, String text) {
        this(tag, text, false);
    }

    public Checkbox(String tag, String text, boolean enabled) {
        super(tag);
        this.text = text;
        this.enabled = enabled;
        this.setSize(CHECKBOX_SIZE, CHECKBOX_SIZE);
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

    protected void onPressed() {}

    @Override
    public void update() {
        this.setWidth(CHECKBOX_SIZE + font.getStringWidth(text) + 2);
        this.setHeight(CHECKBOX_SIZE);
    }

    /**
     * @return An int array of the dimensions of the check box.
     * */
    public int[] getCheckbox() {
        return new int[] { getX(), getY(), CHECKBOX_SIZE, CHECKBOX_SIZE };
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void setFont(FontData font) {
        super.setFont(font);
        this.update();
    }
}
