package net.halalaboos.huzuni.api.settings;

import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * Node that consists of a single string. That's it.
 * */
public class StringNode extends Node {
		
	private final String defaultText;
	
	private String text;
	
	public StringNode(String name, String defaultText, String description) {
		super(name, description);
		this.text = defaultText;
		this.defaultText = defaultText;
	}
	
	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		json.addProperty(getName(), text);
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			text = json.get(getName()).getAsString();
		}
	}

	public String getDefaultText() {
		return defaultText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean hasText() {
		return !text.isEmpty();
	}

}
