package net.halalaboos.huzuni.indev.gui.containers;

import com.sun.javafx.scene.traversal.Direction;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.InputUtility;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;
import net.halalaboos.huzuni.indev.gui.actions.WheelActionListener;
import net.halalaboos.huzuni.indev.gui.layouts.ScrollLayout;

/**
 * Implementation of a scrollable container. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class ScrollableContainer extends Container {

    private ScrollbarNew verticalScrollbar, horizontalScrollbar;

    public ScrollableContainer(String tag) {
        super(tag);
        this.verticalScrollbar = new ScrollbarNew(inputUtility, 8);
        this.horizontalScrollbar = new ScrollbarNew(inputUtility, false,8);
        this.setLayout(new ScrollLayout());
        this.setLayering(false);

        // Sets the SCROLLBAR activated
        this.addListener(Actions.MOUSEPRESS, (ClickAction.ClickActionListener) action -> updateMouseClick(action.x, action.y));
        // Sets scrolling to false once the mouse button is released.
        this.addListener(Actions.MOUSERELEASE, (ClickAction.ClickActionListener) action -> {
            verticalScrollbar.setScrolling(false);
            horizontalScrollbar.setScrolling(false);
            return false;
        });

        // Scrolls when the mouse wheel is moved.
        this.addListener(Actions.MOUSEWHEEL, (WheelActionListener) direction -> updateMouseWheel(direction == Direction.UP ? -1 : 1));
    }

    @Override
    public void update() {
        verticalScrollbar.update();
        horizontalScrollbar.update();
        layout();
        super.update();
    }

    /**
     * @return True if this container's SCROLLBAR was clicked and if this container's SCROLLBAR has been clicked upon.
     * */
    private boolean updateMouseClick(int mouseX, int mouseY){
        if (isHovered()) {
            if (verticalScrollbar.has() && verticalScrollbar.isPointInside(mouseX, mouseY)) {
                verticalScrollbar.setScrolling(true);
                return true;
            } else if (horizontalScrollbar.has() && horizontalScrollbar.isPointInside(mouseX, mouseY)) {
                horizontalScrollbar.setScrolling(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the scroll bar based on the mouse wheel.
     * */
    private boolean updateMouseWheel(int direction) {
        if (isHovered() && isPointInside(inputUtility.getMouseX(), inputUtility.getMouseY())) {
            verticalScrollbar.wheel(direction);
            horizontalScrollbar.wheel(direction);
            return true;
        }
        return false;
    }

    /**
     * @return The area within the component that can be rendered within.
     * */
    public int[] getRenderArea() {
        return new int[] {
                getX(),
                getY(),
                getWidth() - (verticalScrollbar.has() ? verticalScrollbar.getBarSize() : 0),
                getHeight() - (horizontalScrollbar.has() ? horizontalScrollbar.getBarSize() : 0)
        };
    }

    @Override
    public void layout() {
        verticalScrollbar.constrict();
        horizontalScrollbar.constrict();
        super.layout();
    }

    @Override
    protected void setInputUtility(InputUtility inputUtility) {
        super.setInputUtility(inputUtility);
        this.verticalScrollbar.setInputUtility(inputUtility);
        this.horizontalScrollbar.setInputUtility(inputUtility);
    }

    public ScrollbarNew getVerticalScrollbar() {
        return verticalScrollbar;
    }

    public ScrollbarNew getHorizontalScrollbar() {
        return horizontalScrollbar;
    }
}
