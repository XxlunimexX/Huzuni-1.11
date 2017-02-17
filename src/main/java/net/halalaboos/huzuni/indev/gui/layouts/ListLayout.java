package net.halalaboos.huzuni.indev.gui.layouts;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Layout;
import net.halalaboos.huzuni.indev.gui.ScrollableContainer;

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

    public ListLayout(int padding, int componentPadding) {
        this.padding = padding;
        this.componentPadding = componentPadding;
    }

    @Override
    public void layout(ScrollableContainer container, List<Component> components) {
        // x and y positions are offset by the scroll offset when necessary.
        int x = container.getX() + padding;
        int y = container.getY() + padding - container.getVerticalScrollbar().getScrollOffset();
        int width = container.getRenderArea()[2];
        // Total area used in calculations within the scroll bar.
        int totalAreaHeight = padding * 2;

        // Position each component within the container as necessary and update the total area.
        for (Component component : components) {
            component.setX(x);
            component.setY(y);
            component.setWidth(width - padding * 2);
            y += component.getHeight() + componentPadding;

            totalAreaHeight += component.getHeight() + componentPadding;

            if (!loaded.contains(component)) {
                loaded.add(component);
                isLoaded = true;
            }
        }

        // If we have more than 0 components, there will be extra padding appended and it is necessary to remove this from the total area.
        if (components.size() > 0)
            totalAreaHeight -= componentPadding;

        // Update the total area for the vertical scroll bar.
        container.getVerticalScrollbar().setTotalAreaLength(totalAreaHeight);
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
