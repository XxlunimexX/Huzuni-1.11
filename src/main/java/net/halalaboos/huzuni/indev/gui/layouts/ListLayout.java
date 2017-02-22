package net.halalaboos.huzuni.indev.gui.layouts;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Layout;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * List styled layout used within scrollable containers. Will lay out each component to be within a list-order. <br/>
 * Created by Brandon Williams on 2/16/2017.
 */
public class ListLayout implements Layout<ScrollableContainer> {

    private final List<Component> loaded = new ArrayList<>();

    // Store a boolean for checking if the loaded list is loaded. This is here so we can force the container to be reloaded.
    private boolean isLoaded = true;

    // Padding used between the components and the container.
    private int padding;

    // Padding used between the components.
    private int componentPadding;

    // Used to determine whether or not this list layout will format the component in a vertical order or a horizontal order.
    private boolean vertical;

    public ListLayout(int padding, int componentPadding) {
        this(padding, componentPadding, true);
    }

    public ListLayout(int padding, int componentPadding, boolean vertical) {
        this.padding = padding;
        this.componentPadding = componentPadding;
        this.vertical = vertical;
    }

    @Override
    public int[] layout(ScrollableContainer container, List<Component> components) {
        // x and y positions are offset by the scroll offset when necessary.
        int x = container.getX() + padding - (vertical ? 0 : container.getHorizontalScrollbar().getScrollOffset());
        int y = container.getY() + padding - (vertical ? container.getVerticalScrollbar().getScrollOffset() : 0);
        int width = container.getRenderArea()[2];
        int height = container.getRenderArea()[3];

        // Total area used in calculations within the scroll bar.
        int totalAreaLength = padding * 2;

        // Position each component within the container as necessary and update the total area.
        for (Component component : components) {
            component.setX(x);
            component.setY(y);

            // Update the position with the components dimensions and apply padding.
            if (vertical) {
                component.setWidth(width - padding * 2);
                y += component.getHeight() + componentPadding;
            } else {
                component.setHeight(height - padding * 2);
                x += component.getWidth() + componentPadding;
            }

            // Update the total area length for calculations done by the scroll bar.
            totalAreaLength += (vertical ? component.getHeight() : component.getWidth()) + componentPadding;

            if (!loaded.contains(component)) {
                loaded.add(component);
                isLoaded = true;
            }
        }

        // If we have more than 0 components, there will be extra padding appended and it is necessary to remove this from the total area.
        if (components.size() > 0)
            totalAreaLength -= componentPadding;

        container.getHorizontalScrollbar().updatePosition(container);
        container.getVerticalScrollbar().updatePosition(container);
        // Update the total area for the scroll bar.
        if (vertical)
            container.getVerticalScrollbar().setTotalAreaLength(totalAreaLength);
        else
            container.getHorizontalScrollbar().setTotalAreaLength(totalAreaLength);
        return new int[] { width, height };
    }

    @Override
    public void updateComponentPositions(Component component) {
        isLoaded = false;
    }

    @Override
    public boolean laidOut(ScrollableContainer container) {
        // Return true if any component is not in the loaded list.
        return !isLoaded && container.getComponents().stream().anyMatch(component -> !loaded.contains(component));
    }
}
