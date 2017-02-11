package net.halalaboos.huzuni.api.settings;

import com.google.gson.JsonObject;

import java.awt.*;
import java.io.IOException;

/**
 * Node which contains a color object.
 * */
public class ColorNode extends Node {
	
	private Color color;
	
	public ColorNode(String name, Color color, String description) {
		super(name, description);
		this.color = color;
	}
	
	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		JsonObject color = new JsonObject();
		color.addProperty("r", this.color.getRed());
		color.addProperty("g", this.color.getGreen());
		color.addProperty("b", this.color.getBlue());
		color.addProperty("a", this.color.getAlpha());
		json.add(getName(), color);
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			JsonObject colorObject = json.getAsJsonObject(getName());
			if (colorObject != null) {
				int red = colorObject.get("r").getAsInt(), green = colorObject.get("g").getAsInt(), blue = colorObject.get("b").getAsInt(), alpha = colorObject.get("a").getAsInt();
				color = new Color(red, green, blue, alpha);
			}
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
