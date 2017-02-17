package net.halalaboos.huzuni.indev.gui.layouts;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Layout;
import net.halalaboos.huzuni.indev.gui.ScrollableContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used within the scrollable container to layout each component vertically. <br/>
 * Created by Brandon Williams on 1/23/2017.
 */
public class ScrollLayout implements Layout<ScrollableContainer> {

    // Store each component once loaded.
    private Map<Component, int[]> positions = new HashMap<>();

    public ScrollLayout() {
    }

    @Override
    public void layout(ScrollableContainer container, List<Component> components) {
        // x and y positions are offset by the scroll offset when necessary.
        int x = container.getX();
        int y = container.getY() - container.getVerticalScrollbar().getScrollOffset();
        int totalAreaHeight = 0;
        for (Component component : components) {
            // Store each position to ensure we don't constantly offset this component.
            if (!positions.containsKey(component)) {
                positions.put(component, new int[] { component.getX(), component.getY() });
            }
            int[] positions = this.positions.get(component);
            component.setX(x + positions[0]);
            component.setY(y + positions[1]);

            // Update the total area height with the furthest component down to ensure that the vertical scrollbar
            if (positions[1] + component.getHeight() > totalAreaHeight)
                totalAreaHeight = positions[1] + component.getHeight();
        }
        // Update the total area for the vertical scroll bar.
        container.getVerticalScrollbar().setTotalAreaLength(totalAreaHeight);
    }

    @Override
    public void updateComponentPositions(Component component) {
        positions.put(component, new int[] { component.getX(), component.getY() });
    }

    @Override
    public boolean laidOut(ScrollableContainer container) {
        // If there are no mapped components and any component not within this positions map implies it has not been laid out properly.
        return !positions.isEmpty() && container.getComponents().stream().anyMatch(component -> !positions.containsKey(component));
    }

}
