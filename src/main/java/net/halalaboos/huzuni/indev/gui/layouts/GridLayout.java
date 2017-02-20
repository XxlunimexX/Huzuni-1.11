package net.halalaboos.huzuni.indev.gui.layouts;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.Layout;
import net.halalaboos.mcwrapper.api.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon Williams on 2/19/2017.
 */
public class GridLayout implements Layout<Container> {

    private final List<Component> loaded = new ArrayList<>();

    private final int rows, columns;

    // Store a boolean for checking if the loaded list is loaded. This is here so we can force the container to be reloaded.
    private boolean isLoaded = true;

    public GridLayout(int rows) {
        this(rows, -1);
    }

    public GridLayout(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public void layout(Container container, List<Component> components) {

        // Each row has two values: width and height.
        // rowSize[row][0] = width
        // rowSize[row][1] = height
        int[][] rowSize = new int[rows][2];

        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);

            // Calculate this component's row.
            int row = MathUtils.floor((double) i / (double) rows);

            // Increase this row's width if necessary.
            if (component.getWidth() > rowSize[row][0])
                rowSize[row][0] = component.getWidth();

            // Increase this row's height if necessary.
            if (component.getHeight() > rowSize[row][1])
                rowSize[row][1] = component.getHeight();

            // Store this component as loaded to allow for the isLaidOut function to perform as expected.
            if (!loaded.contains(component)) {
                loaded.add(component);
                isLoaded = true;
            }
        }

        int x = 0, y = 0;
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);

            // Calculate this component's row.
            int row = MathUtils.floor((double) i / (double) rows);

            component.setX(container.getX() + x);
            component.setY(container.getY() + y);

            if (x + component.getWidth() > container.getWidth())
                container.setWidth(x + component.getWidth());
            if (y + component.getHeight() > container.getHeight())
                container.setHeight(y + component.getHeight());

            x += rowSize[row][0];

            // If the next component's index indicates it is in the next row, we move down to the next row.
            if (MathUtils.floor((double) (i + 1) / (double) rows) > row) {
                x = 0;
                y += rowSize[row][1];
            }
        }
    }

    @Override
    public void updateComponentPositions(Component component) {
        isLoaded = false;
    }

    @Override
    public boolean laidOut(Container container) {
        // Return true if any component is not in the loaded list.
        return !isLoaded && container.getComponents().stream().anyMatch(component -> !loaded.contains(component));
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
