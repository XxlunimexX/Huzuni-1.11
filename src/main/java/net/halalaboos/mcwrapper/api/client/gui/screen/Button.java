package net.halalaboos.mcwrapper.api.client.gui.screen;

import net.halalaboos.mcwrapper.api.util.enums.MouseButton;

import java.util.function.BiFunction;

public class Button {

	private BiFunction<Button, MouseButton, Boolean> onClick;

	private String text;

	private int x, y;
	private int width = 100, height = 20;

	private int id;

	public Button(int id) {
		this.id = id;
	}

	public Button(int id, int x, int y, int width, int height, String text) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}

	public Button setText(String text) {
		this.text = text;
		return this;
	}

	public Button onClick(BiFunction<Button, MouseButton, Boolean> onClick) {
		this.onClick = onClick;
		return this;
	}

	public Button setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Button setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public BiFunction<Button, MouseButton, Boolean> getOnClick() {
		return onClick;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getText() {
		return text;
	}
}
