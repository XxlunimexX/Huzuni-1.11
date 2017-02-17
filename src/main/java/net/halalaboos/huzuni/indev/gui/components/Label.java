package net.halalaboos.huzuni.indev.gui.components;

import net.halalaboos.huzuni.indev.gui.Component;

import java.awt.*;

/**
 * Simple label implementation. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class Label extends Component {

    private String text;

    private Color color;

    public Label(String tag, String text) {
        this(tag, text, Color.WHITE);
    }

    public Label(String tag, String text, Color color) {
        super(tag);
        this.text = text;
        this.color = color;
    }

    @Override
    public void update() {
        this.setWidth(font.getStringWidth(text));
        this.setHeight(font.getFontHeight());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
