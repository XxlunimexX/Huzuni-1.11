package net.halalaboos.huzuni.indev.gui.containers;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Toolbox;

/**
 * Basic scrollbar logic. <br/>
 * Created by Brandon Williams on 2/16/2017.
 */
public class Scrollbar {

    /**
     * Minimum length allowed for any scroll bar.
     * */
    public static final int MINIMUM_SCROLLBAR_SIZE = 12;

    private Toolbox toolbox;

    // X and Y positions of this scroll bar.
    private int x, y;

    // Length of the area this scroll bar occupies.
    private int barLength;

    // Size of the moveable bar.
    private int barSize;

    // The actual offset this scrollbar has.
    private int scrollOffset;

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

    //
    private float scrollVelocity = 0;

    public Scrollbar(Toolbox toolbox, int barSize) {
        this(toolbox, true, barSize);
    }

    public Scrollbar(Toolbox toolbox, boolean vertical, int barSize) {
        this.toolbox = toolbox;
        this.vertical = vertical;
        this.barSize = barSize;
    }

    /**
     * Updates the scrolling percentage and also constricts it's value to between 0 and 1.
     * */
    public void update() {
        if (this.scrolling && has()) {
            /** This scroll offset is calculated with the following:
             percentage = (mouse position - offset) / (total area - scrollbar length)
             offset = percentage * (total area - view area)
             But the actual calculation looks like this:

              (mouse position - offset) * (total area - view area)
              -----------------(divided by)-----------------------
                      (total area - scrollbar length)
            */
            this.scrollOffset = (int) ((float) (((vertical ? toolbox.getMouseY() : toolbox.getMouseX()) - mouseOffset) * (totalAreaLength - viewableAreaLength)) / (float) (viewableAreaLength - getScrollbarLength()));
        } else if (scrollVelocity != 0F) {
            // If the velocity is coming to a crawl..
            if (scrollVelocity <= 0.5F && scrollVelocity >= -0.5F) {
                // Reset it.
                scrollVelocity = 0F;
            } else {
                // Otherwise increment the offset and decrement the velocity.
                this.scrollOffset += scrollVelocity;
                scrollVelocity *= 0.95F;
            }
        }
        constrict();
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
        if (scrollOffset > (totalAreaLength - viewableAreaLength))
            scrollOffset = (totalAreaLength - viewableAreaLength);
        if (scrollOffset < 0 || !has())
            scrollOffset = 0;

        // Constricts the scrolling velocity.
        float maxVelocity = 6F;
        if (scrollVelocity >= maxVelocity)
            scrollVelocity = maxVelocity;
        if (scrollVelocity <= -maxVelocity)
            scrollVelocity = -maxVelocity;
    }

    /**
     * Sets this scrollbar to scrolling and updates the mouse offset.
     * */
    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
        this.mouseOffset = (vertical ? toolbox.getMouseY() : toolbox.getMouseX()) - getScrollbarPosition();
    }

    /**
     * Invoked to update the scroll percentage using the mouse wheel.
     * */
    public void wheel(int direction) {
        if (has()) {
            //System.out.println(-direction / (((float) totalAreaLength / (float) (viewableAreaLength)) * ((float) totalAreaLength / (float) (viewableAreaLength))));
            scrollVelocity += -direction;
            constrict();
        }
    }

    /**
     * @return The offset used for rendering and calculating the inside area.
     * */
    public int getScrollOffset() {
        return has() ? scrollOffset : 0;
    }

    /**
     * @return A float value between 0 ~ 1 which represents the percentage this scrollbar has scrolled.
     * */
    public float getScrollPercentage() {
        return has() ? (float) scrollOffset / (float) (totalAreaLength - viewableAreaLength) : 0;
    }

    /**
     * @return True if the x and y positions are within this scroll bar's area.
     * */
    public boolean isPointInside(int x, int y) {
        return toolbox.isPointInside(x, y, getArea());
    }

    /**
     * @return True if the x and y positions are within the moveable scroll bar.
     * */
    public boolean isPointInsideBar(int x, int y) {
        return toolbox.isPointInside(x, y, getScrollbar());
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
        // This calculation is used to find the position of the moveable scroll bar within the SCROLLBAR area.
        // percentage * ((viewable area) - SCROLLBAR length)
        return (int) (getScrollPercentage() * (float) ((viewableAreaLength) - getScrollbarLength()));
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

    protected void setToolbox(Toolbox toolbox) {
        this.toolbox = toolbox;
    }
}
