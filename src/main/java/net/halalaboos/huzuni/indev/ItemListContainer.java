package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.api.node.ItemList;
import net.halalaboos.huzuni.api.node.Nameable;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.layouts.ListLayout;

/**
 * Created by Brandon Williams on 2/25/2017.
 */
public class ItemListContainer extends ScrollableContainer {

    public ItemListContainer(ItemList<Nameable> itemList, FontData itemFont) {
        super("invisible");
        this.setLayout(new ListLayout(0, 1));
        this.setAutoLayout(true);
        this.setUseLayoutSize(true);
        this.setTooltip(itemList.getDescription());
        for (Nameable item : itemList.getItems()) {
            Label label = new Label("item", item.getName());
            label.setFont(itemFont);
            this.add(label);
        }
    }
}
