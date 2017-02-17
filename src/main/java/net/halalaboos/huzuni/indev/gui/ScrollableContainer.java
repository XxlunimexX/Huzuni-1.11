package net.halalaboos.huzuni.indev.gui;

import com.sun.javafx.scene.traversal.Direction;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;
import net.halalaboos.huzuni.indev.gui.actions.WheelActionListener;
import net.halalaboos.huzuni.indev.gui.layouts.ScrollLayout;

/**
 * Implementation of a scrollable container. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class ScrollableContainer extends Container {

    private Scrollbar verticalScrollbar;

    public ScrollableContainer(String tag) {
        super(tag);
        this.verticalScrollbar = new Scrollbar(inputUtility, 8);
        this.setLayout(new ScrollLayout());
        this.setLayering(false);

        // Sets the scrollbar activated
        this.addListener(Actions.MOUSEPRESS, (ClickAction.ClickActionListener) action -> updateMouseClick(action.x, action.y));
        // Sets scrolling to false once the mouse button is released.
        this.addListener(Actions.MOUSERELEASE, (ClickAction.ClickActionListener) action -> {
            verticalScrollbar.setScrolling(false);
            return false;
        });

        // Scrolls when the mouse wheel is moved.
        this.addListener(Actions.MOUSEWHEEL, (WheelActionListener) direction -> updateMouseWheel(direction == Direction.UP ? -1 : 1));
    }

    @Override
    public void update() {
       super.update();
       verticalScrollbar.updatePosition(this);
       verticalScrollbar.update();
       layout();
    }

    /**
     * @return True if this container's scrollbar was clicked and if this container's scrollbar has been clicked upon.
     * */
    protected boolean updateMouseClick(int mouseX, int mouseY){
        if (isHovered() && verticalScrollbar.has() && verticalScrollbar.isPointInside(mouseX, mouseY)) {
            verticalScrollbar.setScrolling(true);
            return true;
        }
        return false;
    }

    /**
     * Updates the scroll bar based on the mouse wheel.
     * */
    protected boolean updateMouseWheel(int direction) {
        if (isHovered() && isPointInside(inputUtility.getMouseX(), inputUtility.getMouseY())) {
            verticalScrollbar.wheel(direction);
            return true;
        }
        return false;
    }

    /**
     * @return The area within the component that can be rendered within.
     * */
    public int[] getRenderArea() {
        if (verticalScrollbar.has()) {
            return new int[] {
                    getX(),
                    getY(),
                    getWidth() - verticalScrollbar.getBarSize(),
                    getHeight()
            };
        } else {
            return new int[] {
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight()
            };
        }
    }

    @Override
    public void layout() {
        verticalScrollbar.constrict();
        super.layout();
    }

    @Override
    protected void setInputUtility(InputUtility inputUtility) {
        super.setInputUtility(inputUtility);
        this.verticalScrollbar.setInputUtility(inputUtility);
    }

    public Scrollbar getVerticalScrollbar() {
        return verticalScrollbar;
    }
}
