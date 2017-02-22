package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.Slider;
import net.halalaboos.huzuni.indev.gui.layouts.GridLayout;

/**
 * Container which holds the title, description, and slider components that are used to represent value nodes. <br/>
 * Created by Brandon Williams on 2/16/2017.
 */
public class ValueContainer extends Container {

    private final Label title, description;

    private final Slider slider;

    private final Value value;

    public ValueContainer(Value value) {
        super("invisible");
        // Using a grid layout with infinite rows and three columns ensures that the components are stacked upon each other.
        this.setLayout(new GridLayout(GridLayout.INFINITE_LENGTH, 3, 0, 1,1));
        this.setUseLayoutSize(true);
        this.value = value;
        this.add(title = new Label("title", value.getName()));
        this.add(description = new Label("description", value.getDescription()));
        this.add(slider = new Slider("slider", value.getName()));
        // Set the default dimensions of the slider as well as updating it's percentage to reflect the value node.
        slider.setSize(150, 12);
        slider.setSliderPercentage((value.getValue() - value.getMinValue()) / (value.getMaxValue() - value.getMinValue()));    }

    @Override
    public void update() {
        // Update the actual value of this value node with the slider information.
        this.value.setValue(value.getMinValue() + (slider.getSliderPercentage() * (value.getMaxValue() - value.getMinValue())));
        // Update the title label to coincide with any value change.
        this.title.setText(String.format("%s (%.1f%s)", value.getName(), value.getValue(), value.getCarot()));
        super.update();
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
