package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.api.node.impl.StringNode;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.layouts.GridLayout;

/**
 * Created by Brandon Williams on 2/23/2017.
 */
public class StringNodeContainer extends Container {

    private final Label title;

    private final StringNode stringNode;

    private final TextField textField;

    public StringNodeContainer(StringNode stringNode, FontData titleFont, FontData textFont) {
        super("invisible");
        this.stringNode = stringNode;
        this.setLayout(new GridLayout(2, GridLayout.INFINITE_LENGTH, 0, 1,1));
        this.setUseLayoutSize(true);
        this.setAutoLayout(true);
        this.add(title = new Label("title", stringNode.getName()));
        this.title.setFont(titleFont);
        this.title.setTooltip(stringNode.getDescription());
        this.add(textField = new TextField("lined", stringNode.getText(), stringNode.getDefaultText()));
        this.textField.setFont(textFont);
        this.textField.setSize(100, textFont.getFontHeight() + 2);
        // this.textField.setTooltip(stringNode.getDescription());
        this.layout();
    }

    @Override
    public void update() {
        // Update the string node.
        this.stringNode.setText(textField.hasText() ? textField.getText() : stringNode.getDefaultText());
        super.update();
    }
}
