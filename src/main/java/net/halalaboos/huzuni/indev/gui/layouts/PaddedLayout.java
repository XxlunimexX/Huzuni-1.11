package net.halalaboos.huzuni.indev.gui.layouts;


import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.Layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic implementation of the layout interface. <br/>
 * Simply saves the components original positions and force them to follow the container's position. <br/>
 * Will enforce a vertical and/or horizontal padding. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class PaddedLayout implements Layout<Container> {

    /**
     * Store the positions of each component.
     * */
    protected final Map<Component, int[]> positions = new HashMap<>();

    protected int verticalPadding, horizontalPadding;

    public PaddedLayout() {
        this(0, 0);
    }

    public PaddedLayout(int padding) {
        this(padding, padding);
    }

    public PaddedLayout(int verticalPadding, int horizontalPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
    }

    @Override
    public int[] layout(Container container, List<Component> components) {
        int width = 0, height = 0;
        for (Component component : components) {
            if (!positions.containsKey(component)) {
                positions.put(component, new int[] { component.getX(), component.getY() });
            }

            int[] positions = this.positions.get(component);
            component.setX(container.getX() + horizontalPadding + positions[0]);
            component.setY(container.getY() + verticalPadding + positions[1]);

            // Calculate the area these components occupy.
            if (positions[0] + component.getWidth() + horizontalPadding * 2 > width)
                width = positions[0] + component.getWidth() + horizontalPadding * 2;
            if (positions[1] + component.getHeight() + verticalPadding * 2 > height)
                height = positions[1] + component.getHeight() + verticalPadding * 2;
        }
        return new int[] { width, height };
    }

    @Override
    public void updateComponentPositions(Component component) {
        positions.put(component, new int[] { component.getX(), component.getY() });
    }

    @Override
    public boolean laidOut(Container container) {
        // If there are no mapped components and any component not within this positions map implies it has not been laid out properly.
        return !positions.isEmpty() && container.getComponents().stream().anyMatch(component -> !positions.containsKey(component));
    }

    public void setPadding(int padding) {
        this.setHorizontalPadding(padding);
        this.setVerticalPadding(padding);
    }

    public int getVerticalPadding() {
        return verticalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
    }

    public int getHorizontalPadding() {
        return horizontalPadding;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }
}
