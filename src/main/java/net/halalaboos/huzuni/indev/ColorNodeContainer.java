package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.api.node.impl.ColorNode;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.layouts.GridLayout;

import java.awt.*;

/**
 * Created by Brandon Williams on 2/23/2017.
 */
public class ColorNodeContainer extends Container {

    private final Label title;

    private final ColorNode colorNode;

    private final TextField textField;

    public ColorNodeContainer(ColorNode colorNode, FontData titleFont, FontData textFont) {
        super("invisible");
        this.colorNode = colorNode;
        this.setLayout(new GridLayout(2, GridLayout.INFINITE_LENGTH, 0, 1,1));
        this.setUseLayoutSize(true);
        this.setAutoLayout(true);
        this.add(title = new Label("title", colorNode.getName()));
        this.title.setFont(titleFont);
        this.title.setTooltip(colorNode.getDescription());
        this.add(textField = new TextField("lined", Integer.toHexString(colorNode.getColor().getRGB()).substring(2).toUpperCase(), "RRGGBB"));
        this.textField.setFont(textFont);
        this.textField.setSize(100, textFont.getFontHeight() + 2);
        this.textField.setColor(colorNode.getColor());
        this.textField.setValidCharacters("0123456789abcdefABDCEF");
        this.textField.setMaxLength(6);
        this.textField.setTooltip("Enter a hexadecimal color!");
        this.layout();
    }

    @Override
    public void update() {
        if (textField.hasText()) {
            Color color = new Color(Integer.decode("#" + textField.getText().toLowerCase()));
            // Update the color node.
            colorNode.setColor(color);
            textField.setColor(color);
        }
        super.update();
    }
}
