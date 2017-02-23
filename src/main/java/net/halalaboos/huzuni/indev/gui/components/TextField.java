package net.halalaboos.huzuni.indev.gui.components;


import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;
import net.halalaboos.huzuni.indev.gui.actions.KeystrokeAction;
import org.lwjgl.input.Keyboard;

/**
 * Simple text field implementation. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class TextField extends Component {

    /**
     * Every character which is allowed to be typed into this text field.
     * */
    private String validCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-=[]\\;',./`~!@#$%^&*()_+{}|:\"<>?; ";

    private String text, defaultText;

    private int pointer = 0, maxLength = 50;

    private boolean typing = false;

    public TextField(String tag, String text) {
        this(tag, text, text);
    }

    public TextField(String tag, String text, String defaultText) {
        super(tag);
        this.setText(text);
        this.defaultText = defaultText;
        this.addListener(Actions.MOUSEPRESS, (ClickAction.ClickActionListener) action -> typing = isHovered() && isPointInside(action.x, action.y) && action.buttonId == 0);
        this.addListener(Actions.KEYSTROKE, (KeystrokeAction.KeystrokeActionListener) action -> {
            if (typing) {
                switch (action.key) {
                    case Keyboard.KEY_BACK:
                        backSpace();
                        break;
                    case Keyboard.KEY_HOME:
                        move(0);
                        break;
                    case Keyboard.KEY_LEFT:
                        this.moveDirection(-1);
                        break;
                    case Keyboard.KEY_RIGHT:
                        this.moveDirection(1);
                        break;
                    case Keyboard.KEY_END:
                        move(this.text.length());
                        break;
                    case Keyboard.KEY_DELETE:
                        forwardSpace();
                        break;
                    default:
                        break;
                }
                if (validCharacters.contains(Character.toString(action.characater)))
                    this.append(action.characater);
                return true;
            }
            return false;
        });
    }

    /**
     * Appends this string to this textfield's text.
     * */
    public void append(String string) {
        if (text.concat(string).length() <= maxLength) {
            if (pointer >= this.text.length()) {
                this.text = this.text.concat(string);
            } else if (pointer > 0) {
                this.text = this.text.substring(0, pointer) + string + this.text.substring(pointer, this.text.length());
            }
            pointer += string.length();
        }
    }

    /**
     * Appends this character to this textfield's text.
     * */
    private void append(char c) {
        append(Character.toString(c));
    }

    @Override
    public void update() {
    }


    /**
     * Performs the backspace operation on the text with the current selection position.
     * */
    private void backSpace() {
        if (!this.text.isEmpty()) {
            if (pointer >= this.text.length()) {
                this.text = this.text.substring(0, this.text.length() - 1);
            } else if (pointer > 0) {
                this.text = this.text.substring(0, pointer - 1) + this.text.substring(pointer, this.text.length());
            }
            moveDirection(-1);
        }
    }

    /**
     * Performs the forward space operation on the text with the current selection position.
     * */
    private void forwardSpace() {
        if (!this.text.isEmpty()) {
            if (pointer <= 0) {
                this.text = this.text.substring(1, this.text.length());
            } else if (pointer < this.text.length()) {
                this.text = this.text.substring(0, pointer) + this.text.substring(pointer + 1, this.text.length());
            }
        }
    }

    /**
     * Moves the selection position to the given index.
     * */
    private void move(int index) {
        pointer = index;
        keepSafe();
    }

    /**
     * Moves the selection position into the direction given.
     * */
    private void moveDirection(int direction) {
        pointer += direction;
        keepSafe();
    }

    /**
     * Keeps the selection position within 0 and the length of the text.
     * */
    private void keepSafe() {
        if (pointer > this.text.length()) {
            pointer = this.text.length();
        } else if (pointer < 0) {
            pointer = 0;
        }
    }

    /**
     * @return A string that can be rendered.
     * */
    public String getRenderText(boolean showPlacement) {
        String text = showPlacement ? this.text.substring(0, pointer) + "|" + this.text.substring(pointer, this.text.length()) : this.text;
        return font.trim(text, this.getWidth() * GLManager.getScaleFactor(), true);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.pointer = text.length();
    }

    /**
     * @return True if this textfield has text.
     * */
    public boolean hasText() {
        return !text.isEmpty();
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isTyping() {
        return typing;
    }

    public String getValidCharacters() {
        return validCharacters;
    }

    public void setValidCharacters(String validCharacters) {
        this.validCharacters = validCharacters;
    }
}