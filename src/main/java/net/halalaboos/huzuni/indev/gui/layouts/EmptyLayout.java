package net.halalaboos.huzuni.indev.gui.layouts;


import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.Layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Empty implementation of the layout interface. <br/>
 * Simply saves the components original positions and force them to follow the container's position. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class EmptyLayout implements Layout<Container> {

    /**
     * Store the positions of each component.
     * */
    protected final Map<Component, int[]> positions = new HashMap<>();

    @Override
    public void layout(Container container, List<Component> components) {
        for (Component component : components) {
            if (!positions.containsKey(component)) {
                positions.put(component, new int[] { component.getX(), component.getY() });
            }
            int[] positions = this.positions.get(component);
            component.setX(container.getX() + positions[0]);
            component.setY(container.getY() + positions[1]);
        }
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

	@Override
	public boolean updateWithContainer() {
		return false;
	}

	@Override
	public void setUpdateWithContainer(boolean updateWithContainer) {

	}

	@Override
	public int[] getPadding() {
		return new int[0];
	}

	@Override
	public void setPadding(int x, int y, int width, int height) {

	}
}
