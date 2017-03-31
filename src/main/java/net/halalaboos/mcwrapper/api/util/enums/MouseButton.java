package net.halalaboos.mcwrapper.api.util.enums;

public enum MouseButton {

	LEFT(0),
	RIGHT(1),
	MIDDLE(2),
	NONE(4);

	private final int index;

	MouseButton(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static MouseButton getMouseButton(int index) {
		if (index > 2 || index < 0) return NONE;
		return values()[index];
	}
}
