package net.halalaboos.huzuni.api.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Node which can contain a list of nameable objects.
 * */
public abstract class ItemList <I extends Nameable> extends Node {
	
	private final List<I> items = new ArrayList<I>();
	
	private boolean ordered = false, expandable = true;
	
	public ItemList(String name, String description) {
		super(name, description);
	}
	
	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		JsonArray array = new JsonArray();
		for (I item : items) {
			JsonObject itemObject = new JsonObject();
			saveItem(itemObject, item);
			array.add(itemObject);
		}
		json.add(getName(), array);

	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			JsonArray objects = json.getAsJsonArray(getName());
			for (int i = 0; i < objects.size(); i++) {
				JsonObject itemObject = (JsonObject) objects.get(i);
				items.add(loadItem(itemObject));
			}
		}
	}
	
	@Override
	public boolean hasNode(JsonObject json) {
		return json.getAsJsonArray(getName()) != null;
	}
	
	protected abstract void saveItem(JsonObject object, I item);

	protected abstract I loadItem(JsonObject object);
	
	public void add(int index, I element) {
		items.add(index, element);
	}

	public boolean add(I e) {
		return items.add(e);
	}

	public void clearChildren() {
		items.clear();
	}

	public boolean contains(Object o) {
		return items.contains(o);
	}

	public I get(int index) {
		return items.get(index);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public I remove(int index) {
		return items.remove(index);
	}

	public boolean remove(Object o) {
		return items.remove(o);
	}

	public int size() {
		return items.size();
	}

	public List<I> getItems() {
		return items;
	}

	public boolean isOrdered() {
		return ordered;
	}

	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}

}
