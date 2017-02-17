package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.Slider;

import java.text.DecimalFormat;

/**
 * Container which holds the title, description, and slider components that are used to represent value nodes. <br/>
 * Created by Brandon Williams on 2/16/2017.
 */
public class ValueContainer extends Container {

    private final Label title, description;

    private final Slider slider;

    private final Value value;

    public ValueContainer(Value value) {
        super("value");
        this.value = value;
        this.add(title = new Label("title", value.getName()));
        this.add(description = new Label("description", value.getDescription()));
        this.add(slider = new Slider("slider", value.getName()));
        this.setSize(20, 20);
    }

    @Override
    public void update() {
        // Update the actual value of this value node with the slider information.
		DecimalFormat decimalFormat = new DecimalFormat("0.0");
        this.value.setValue(value.getMinValue() + (slider.getSliderPercentage() * (value.getMaxValue() - value.getMinValue())));
        // Update the title label to coincide with any value change.
        this.title.setText(String.format("%s (%s%s)", value.getName(), decimalFormat.format(value.getValue()), value.getCarot()));
        super.update();

        // Update their positions if necessary..
        if (title.getY() != this.description.getHeight()) {
            this.description.setPosition(0, title.getHeight());
            this.getLayout().updateComponentPositions(description);
        }
        if (this.slider.getX() != title.getWidth()) {
            this.slider.setPosition(title.getWidth(), 0);
            this.getLayout().updateComponentPositions(slider);
        }
        this.layout();
    }

    public Value getValue() {
        return value;
    }

    public Label getTitle() {
        return title;
    }

    public Label getDescription() {
        return description;
    }

    public Slider getSlider() {
        return slider;
    }
}
