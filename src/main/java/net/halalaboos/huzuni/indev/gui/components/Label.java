package net.halalaboos.huzuni.indev.gui.components;

import net.halalaboos.huzuni.indev.gui.FontData;
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
        this(tag, text, new FontData(), Color.WHITE);
    }

    public Label(String tag, String text, FontData font) {
        this(tag, text, font, Color.WHITE);
    }

    public Label(String tag, String text, FontData font, Color color) {
        super(tag);
        this.font = font;
        this.text = text;
        this.color = color;
        this.update();
    }

    @Override
    public void update() {
        if (this.font.hasFont()) {
            this.setWidth(font.getStringWidth(text));
            this.setHeight(font.getFontHeight());
        }
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

    @Override
    public void setFont(FontData font) {
        super.setFont(font);
        this.update();
    }
}
