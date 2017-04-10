package net.halalaboos.huzuni.api.node;

import com.google.gson.JsonObject;
import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;

import java.io.IOException;
import java.util.Arrays;

/**
 * Node which contains an array of items. Each item can be selected.
 * */
public class Mode <I> extends Node {
	
	private int selectedItem = 0;
	
	private I[] items;
	
	@SafeVarargs
	public Mode(String name, String description, I... items) {
		super(name, description);
		this.items = items;
	}
	
	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		json.addProperty(getName(), selectedItem);
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (this.hasNode(json))
			setSelectedItem(json.get(getName()).getAsInt());
	}
	
	public int getSelected() {
		return selectedItem;
	}
	
	public I getSelectedItem() {
		return items[selectedItem];
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
		if (this.selectedItem >= items.length)
			this.selectedItem = 0;
		if (this.selectedItem < 0)
			this.selectedItem = items.length - 1;
	}
	
	public void setSelectedItem(I item) {
		for (int i = 0; i < items.length; i++) {
			if (item == items[i]) {
				setSelectedItem(i);
				break;
			}
		}
	}

	public I[] getItems() {
		return items;
	}

	/**
	 * @return The item associated with the name.
	 * */
	public I getItem(String name) {
		for (I object : items)
			if (equals(object, name))
				return object;
		return null;
	}

	/**
	 * @return True if the item's associated name is equal to the string provided.
	 * */
	public boolean equals(I item, String name) {
		return getName(item).replaceAll(" ", "").toLowerCase().equals(name.replaceAll(" ", "").toLowerCase());
	}

	/**
	 * @return The name associated with the item.
	 * */
	public String getName(int i) {
		return getName(items[i]);
	}

	/**
	 * @return The name associated with the item.
	 * */
	public String getName(I item) {
		return item instanceof Nameable ? ((Nameable) item).getName() : item.toString();
	}

	/**
	 * @return A string of all item names in order.
	 * */
	public String getItemNames() {
		String list = "{ ";
		for (int i = 0; i < items.length; i++) {
			list += TextColor.YELLOW + getName(items[i]) + TextColor.RESET + ", ";
		}
		return list.substring(0, list.length() - 2) + " }";
	}

	/**
	 * Adds the item to the array of items.
	 * */
	public void add(I item) {
		I[] newItems = Arrays.copyOf(items, items.length + 1);
		newItems[items.length] = item;
		items = newItems;
	}
}
