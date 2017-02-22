package net.halalaboos.huzuni.indev.gui.layouts;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.Layout;
import net.halalaboos.mcwrapper.api.util.math.MathUtils;

import java.util.List;

/**
 * Layout which places each component into their own row and column. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class GridLayout implements Layout<Container> {

    public static int INFINITE_LENGTH = -1;

    private final int rows, columns;

    private final int verticalPadding;

    private final int horizontalPadding;

    private final int componentPadding;

    // Store a boolean for checking if the loaded list is loaded. This is here so we can force the container to be reloaded.
    private boolean isLoaded = true;

    public GridLayout(int rows) {
        this(rows, INFINITE_LENGTH);
    }

    public GridLayout(int rows, int columns) {
        this(rows, columns, 0);
    }

    public GridLayout(int rows, int columns, int padding) {
        this(rows, columns, padding, padding);
    }

    public GridLayout(int rows, int columns, int containerPadding, int componentPadding) {
        this(rows, columns, containerPadding, containerPadding, componentPadding);
    }

    public GridLayout(int rows, int columns, int verticalPadding, int horizontalPadding, int componentPadding) {
        this.rows = rows;
        this.columns = columns;
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        this.componentPadding = componentPadding;
    }

    @Override
    public int[] layout(Container container, List<Component> components) {

        // Each row has two values: width and height.
        // cellSize[row][column][0] = width
        // cellSize[row][column][1] = height
        int[][][] cellSize = new int[rows == INFINITE_LENGTH ? MathUtils.ceil((float) components.size() / (float) columns) : rows][columns == INFINITE_LENGTH ? MathUtils.ceil((float) components.size() / (float) rows) : columns][2];

        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);

            // Calculate this component's row and column.
            //int row = rows == INFINITE_LENGTH ? 0 : i % rows;
            //int column = columns == INFINITE_LENGTH ? 0 : MathUtils.floor((double) i / (double) columns);
            int row = getPosition(rows != INFINITE_LENGTH, i, rows != INFINITE_LENGTH ? rows : components.size());
            int column = getPosition(rows == INFINITE_LENGTH, i, rows == INFINITE_LENGTH ? columns : components.size());

            // Increase this row's width if necessary.
            if (component.getWidth() > cellSize[row][column][0])
                cellSize[row][column][0] = component.getWidth();

            // Increase this row's height if necessary.
            if (component.getHeight() > cellSize[row][column][1])
                cellSize[row][column][1] = component.getHeight();
        }

        int x = 0, y = 0, width = 0, height = 0;

        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);

            // Calculate this component's row and column.
            int row = getPosition(rows != INFINITE_LENGTH, i, rows != INFINITE_LENGTH ? rows : components.size());
            int column = getPosition(rows == INFINITE_LENGTH, i, rows == INFINITE_LENGTH ? columns : components.size());

            component.setX(container.getX() + horizontalPadding + x);
            component.setY(container.getY() + verticalPadding + y);

            // Increment our width and height
            if (x + component.getWidth() + horizontalPadding * 2 > width)
                width = x + component.getWidth() + horizontalPadding * 2;
            if (y + component.getHeight() + verticalPadding * 2 > height)
                height = y + component.getHeight() + verticalPadding * 2;

            // If we have a next component
            if ((i + 1) < components.size()) {
                // If we have a finite amount of rows and either an infinite amount of columns or a finite amount of columns...
                if (rows > 0 && (columns == INFINITE_LENGTH || columns > 0)) {
                    // And the next index indicates a new row...
                    if ((i + 1) % rows > row) {
                        // Increment the x position (move to the next row)
                        x += cellSize[row][column][0] + componentPadding;
                        // Or if the next index indicates the start of a new column...
                    } else if ((i + 1) % rows == 0) {
                        // Reset the x position (enter the first row) and increment the y position (go down a column)
                        x = 0;
                        y += cellSize[row][column][1] + componentPadding;
                    }
                    // If we have an infinite amount of rows and a finite amount of columns...
                } else if (rows == INFINITE_LENGTH && columns > 0) {
                    // And the next index indicates a new column...
                    if ((i + 1) % columns > column) {
                        // Increment the y position (move to the next column)
                        y += cellSize[row][column][1] + componentPadding;
                        // Or if the next index indicates the start of a new row...
                    }  else if ((i + 1) % columns == 0) {
                        // Reset the y position (enter the first column) and increment the x position (go down a row)
                        y = 0;
                        x += cellSize[row][column][0] + componentPadding;
                    }
                }
            }
        }
        isLoaded = true;
        return new int[] { width, height };
    }

    @Override
    public void updateComponentPositions(Component component) {
        isLoaded = false;
    }

    @Override
    public boolean laidOut(Container container) {
        return isLoaded;
    }

    /**
     * @return
     * */
    private int getPosition(boolean conditional, int index, int size) {
        // If rows are infinite and the other position is finite, then use modulus.
        if (conditional) {
            return index % size;
        }
        // Otherwise, use division and floor it.
        return MathUtils.floor((double) index / (double) size);
    }
}
