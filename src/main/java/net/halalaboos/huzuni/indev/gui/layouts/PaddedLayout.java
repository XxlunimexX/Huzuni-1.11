package net.halalaboos.huzuni.indev.gui.layouts;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.Layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic layout which enforces a padding to be between the edges of the container and it's component. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class PaddedLayout implements Layout<Container> {

    /**
     * Store the positions of each component.
     * */
    protected final Map<Component, int[]> positions = new HashMap<>();

    private final int horizontalPadding, verticalPadding;

    public PaddedLayout(int padding) {
        this(padding, padding);
    }

    public PaddedLayout(int horizontalPadding, int verticalPadding) {
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
    }

    public PaddedLayout() {
        this(1);
    }

    @Override
    public void layout(Container container, List<Component> components) {
        for (Component component : components) {
            if (!positions.containsKey(component)) {
                positions.put(component, new int[] { component.getX(), component.getY() });
            }
            int[] positions = this.positions.get(component);
            component.setX(container.getX() + horizontalPadding + positions[0]);
            component.setY(container.getY() + verticalPadding + positions[1]);

            if (positions[0] + component.getWidth() > container.getWidth() - horizontalPadding * 2)
                container.setWidth(positions[0] + component.getWidth() + horizontalPadding * 2);
            if (positions[1] + component.getHeight() > container.getHeight() - verticalPadding * 2)
                container.setHeight(positions[1] + component.getHeight() + verticalPadding * 2);
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
