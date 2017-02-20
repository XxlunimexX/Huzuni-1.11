package net.halalaboos.huzuni.indev.gui.layouts;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.Layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Brandon Williams on 2/19/2017.
 */
public abstract class BasicLayout <T extends Container> implements Layout <T> {

    /**
     * Store the positions of each component.
     * */
    protected final Map<Component, int[]> positions = new HashMap<>();

    protected int[] padding = new int[] { 0, 0, 0, 0 };

    protected boolean updateWithContainer = true;

    @Override
    public void layout(T container, List<Component> components) {
        for (Component component : components) {
            if (!positions.containsKey(component)) {
                positions.put(component, new int[] { component.getX(), component.getY() });
            }

            if (updateWithContainer) {
                int[] position = positions.get(component);
                component.setPosition(container.getX() + position[0], container.getY() + position[1]);
            }
        }
        layout(container, components, positions);
    }

    protected abstract void layout(T container, List<Component> components, Map<Component, int[]> positions);

    @Override
    public void updateComponentPositions(Component component) {
        positions.put(component, new int[] { component.getX(), component.getY() });
    }

    @Override
    public boolean updateWithContainer() {
        return this.updateWithContainer;
    }

    @Override
    public void setUpdateWithContainer(boolean updateWithContainer) {
        this.updateWithContainer = updateWithContainer;
    }

    @Override
    public int[] getPadding() {
        return padding;
    }

    @Override
    public void setPadding(int x, int y, int width, int height) {
        this.padding = new int[] { x, y, width, height };
    }

    @Override
    public boolean laidOut(Container container) {
        // If there are no mapped components and any component not within this positions map implies it has not been laid out properly.
        return !positions.isEmpty() && container.getComponents().stream().anyMatch(component -> !positions.containsKey(component));
    }
}
