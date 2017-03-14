package net.halalaboos.huzuni.indev.gui.components;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;

/**
 * Simple combo box implementation. <br/>
 * Created by Brandon Williams on 3/5/2017.
 */
public class ComboBox<I> extends Component {

    private I[] items;

    private boolean expanded = false;

    private int selected = 0;

    private int itemHeight = 15;

    public ComboBox(String tag, I... items) {
        super(tag);
        this.items = items;
        addListener(Actions.MOUSEPRESS, (ClickAction.ClickActionListener) action -> isHovered() && isPointInside(action.x, action.y) && action.buttonId == 0);
        addListener(Actions.MOUSERELEASE, (ClickAction.ClickActionListener) action -> {
            if (isHovered() && isPointInside(action.x, action.y) && action.buttonId == 0) {
                if (expanded) {
                    // Find the hovered item and set it selected.
                    int index = getHoveredItem(action.x, action.y);
                    if (index != -1) {
                        setSelected(index);
                    }
                    expanded = false;
                } else {
                    // Expand me!
                    expanded = true;
                }
            } else {
                expanded = false;
            }
            return false;
        });
        this.setHeight(itemHeight);
    }

    @Override
    public void update() {
        // Update the height with the font height + font height * items length.
        setHeight(itemHeight + (expanded ? itemHeight * items.length : 0));
    }
    @Override
    public boolean isPointInside(int x, int y) {
        return super.isPointInside(x, y) || getHoveredItem(x, y) != -1;
    }

    /**
     * @return -1 for no items.
     * */
    public int getHoveredItem(int x, int y) {
        if (expanded) {
            for (int i = 0; i < items.length; i++) {
                if (toolbox.isPointInside(x, y, getItemArea(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @return The area of the item at the index given.
     * */
    public int[] getItemArea(int index) {
        return new int[] { getX(), getY() + itemHeight + index * itemHeight, getWidth(), itemHeight };
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public I[] getItems() {
        return items;
    }

    public void setItems(I[] items) {
        this.items = items;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        if (this.selected < 0)
            this.selected = 0;
        if (this.selected >= items.length)
            this.selected = items.length - 1;
    }

    public I getSelectedItem() {
        return items[selected];
    }
}
