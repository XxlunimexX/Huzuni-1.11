package net.halalaboos.huzuni.api.node.impl;

import com.google.gson.JsonObject;
import net.halalaboos.huzuni.api.node.Node;

import java.io.IOException;
import java.util.Random;

/**
 * A node which contains a float value with a minimum and maximum value. <br/>
 * Each value can contain a carot which represents the type of data this float value represents.
 * */
public class Value extends Node {

	private final String carot;
	
	private float minValue, value, maxValue, incrementValue;
	
	public Value(String name, String carot, float minValue, float value, float maxValue, String description) {
    	this(name, carot, minValue, value, maxValue, -1, description);
    }

	public Value(String name, float minValue, float value, float maxValue, String description) {
    	this(name, "", minValue, value, maxValue, -1, description);
    }

	public Value(String name, float minValue, float value, float maxValue, float incrementValue, String description) {
    	this(name, "", minValue, value, maxValue, incrementValue, description);
    }
    
	public Value(String name, String carot, float minValue, float value, float maxValue, float incrementValue, String description) {
		super(name, description);
		this.carot = carot;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.incrementValue = incrementValue;
		this.value = value;
	}

	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		json.addProperty(getName(), value);
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			value = json.get(getName()).getAsNumber().floatValue();
		}
	}
	
	public String getCarot() {
		return carot;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
		if (this.value < minValue)
			this.value = minValue;
        if (this.value > maxValue)
			this.value = maxValue;
	}

	/**
	 * @return a randomly generated value between the min and max of this value object.
	 * */
	public float getRandom(Random random) {
		return (minValue + maxValue * random.nextFloat());
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public float getIncrementValue() {
		return incrementValue;
	}

	public void setIncrementValue(float incrementValue) {
		this.incrementValue = incrementValue;
	}
	
}
