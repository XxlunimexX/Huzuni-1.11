package net.halalaboos.huzuni.indev.gui.containers;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.InputUtility;

/**
 * Basic scrollbar logic. <br/>
 * Created by Brandon Williams on 2/16/2017.
 */
public class Scrollbar {

    /**
     * Minimum length allowed for any scroll bar.
     * */
    public static final int MINIMUM_SCROLLBAR_SIZE = 12;

    private InputUtility inputUtility;

    // X and Y positions of this scroll bar.
    private int x, y;

    // Length of the area this scroll bar occupies.
    private int barLength;

    // Size of the moveable bar.
    private int barSize;

    // Float from 0 ~ 1 representing the percentage scrolled.
    private float scrolledPercentage;

    // Total length of the area this scroll bar is moveable within.
    private int totalAreaLength;

    // Length of the viewable area.
    private int viewableAreaLength;

    // True if the scroll bar will be vertical, false otherwise.
    private boolean vertical;

    // True if the scroll bar is being moved, false otherwise.
    private boolean scrolling;

    // Stored offset of the mouse position to ensure that the percentage is calculated correctly.
    private int mouseOffset;

    public Scrollbar(InputUtility inputUtility, int barSize) {
        this(inputUtility, true, barSize);
    }

    public Scrollbar(InputUtility inputUtility, boolean vertical, int barSize) {
        this.inputUtility = inputUtility;
        this.vertical = vertical;
        this.barSize = barSize;
    }

    /**
     * Updates the scrolling percentage and also constricts it's value to between 0 and 1.
     * */
    public void update() {
        if (this.scrolling && has()) {
            /*
              Percentage is calculated as:
              percentage = (mouse position) / ((length of container) - (length of scrollbar))
             */
            this.scrolledPercentage = (float) ((vertical ? inputUtility.getMouseY() : inputUtility.getMouseX()) - mouseOffset) / (float) ((viewableAreaLength) - getScrollbarLength());
            constrict();
        }
    }

    /**
     * Updates this scrollbar's position to reflect the expected position within the component provided.
     * */
    public void updatePosition(Component component) {
        if (vertical) {
            setX(component.getX() + component.getWidth() - barSize);
            setY(component.getY());
            setViewableAreaLength(component.getHeight());
            setBarLength(component.getHeight());
        } else {
            setX(component.getX());
            setY(component.getY() + component.getHeight() - barSize);
            setViewableAreaLength(component.getWidth());
            setBarLength(component.getWidth());
        }
    }

    /**
     * Constricts the scrollbar percentage to ensure that it stays between 0 ~ 1.
     * */
    public void constrict() {
        // Constrict the scroll percentage.
        if (scrolledPercentage > 1F)
            scrolledPercentage = 1F;
        if (scrolledPercentage < 0F || !has())
            scrolledPercentage = 0F;
    }

    /**
     * Sets this scrollbar to scrolling and updates the mouse offset.
     * */
    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
        this.mouseOffset = (vertical ? inputUtility.getMouseY() : inputUtility.getMouseX()) - getScrollbarPosition();
    }

    /**
     * Invoked to update the scroll percentage using the mouse wheel.
     * */
    public void wheel(int direction) {
        if (has()) {
            scrolledPercentage += -direction / (((float) totalAreaLength / (float) (viewableAreaLength)) * ((float) totalAreaLength / (float) (viewableAreaLength)));
            constrict();
        }
    }

    /**
     * @return The offset used for rendering and calculating the inside area.
     * */
    public int getScrollOffset() {
        return has() ? (int) (scrolledPercentage * (float) (totalAreaLength - (viewableAreaLength))) : 0;
    }

    /**
     * @return True if the x and y positions are within this scroll bar's area.
     * */
    public boolean isPointInside(int x, int y) {
        return inputUtility.isPointInside(x, y, getArea());
    }

    /**
     * @return True if the x and y positions are within the moveable scroll bar.
     * */
    public boolean isPointInsideBar(int x, int y) {
        return inputUtility.isPointInside(x, y, getScrollbar());
    }


    /**
     * @return The total area of this scroll bar.
     * */
    public int[] getArea() {
        return new int[] { x, y, vertical ? barSize : barLength, vertical ? barLength : barSize };
    }

    /**
     * @return The area of the moveable scroll bar.
     * */
    public int[] getScrollbar() {
        int scrollbarPosition = getScrollbarPosition();
        int scrollbarLength = getScrollbarLength();
        return new int[] {
                x + (vertical ? 0 : scrollbarPosition),
                y + (vertical ? scrollbarPosition : 0),
                vertical ? barSize : scrollbarLength,
                vertical ? scrollbarLength : barSize
        };
    }

    /**
     * @return The position of this scroll bar.
     * */
    public int getScrollbarPosition() {
        // This calculation is used to find the position of the moveable scroll bar within the scrollbar area.
        // percentage * ((viewable area) - scrollbar length)
        return (int) (scrolledPercentage * (float) ((viewableAreaLength) - getScrollbarLength()));
    }

    /**
     * @return The total length of the moveable area of the scrollbar.
     * */
    public int getScrollbarLength() {
        // ratio = (viewable area) /(total area)
        float ratio = (float) (viewableAreaLength) / (float) totalAreaLength;
        if (ratio * (viewableAreaLength) < MINIMUM_SCROLLBAR_SIZE)
            return MINIMUM_SCROLLBAR_SIZE;
        else
            return (int) (ratio * (viewableAreaLength));
    }

    /**
     * @return True if the total area of this scroll bar is greater than the viewable area, meaning this scroll bar is usable.
     * */
    public boolean has() {
        return this.totalAreaLength > viewableAreaLength;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getBarLength() {
        return barLength;
    }

    public void setBarLength(int barLength) {
        this.barLength = barLength;
    }

    public int getBarSize() {
        return barSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
    }

    public int getTotalAreaLength() {
        return totalAreaLength;
    }

    public void setTotalAreaLength(int totalAreaLength) {
        this.totalAreaLength = totalAreaLength;
    }

    public int getViewableAreaLength() {
        return viewableAreaLength;
    }

    public void setViewableAreaLength(int viewableAreaLength) {
        this.viewableAreaLength = viewableAreaLength;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public boolean isScrolling() {
        return scrolling;
    }

    protected void setInputUtility(InputUtility inputUtility) {
        this.inputUtility = inputUtility;
    }
}
