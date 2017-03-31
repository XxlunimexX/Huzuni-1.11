package net.halalaboos.mcwrapper.api.util.enums;

import java.util.Locale;

/**
 * Minecraft uses special formatting codes for their text rendering.  These formatting codes can apply color changes,
 * effects, etc.
 * <p>Using these formatting codes is pretty straightforward.  All that needs to be done is call the formatting code
 * in a method that uses text rendering.  </p>
 * <p>For example, <code>getTextRenderer().render(TextColor.GOLD + "Hello world!", x, y)</code> Would render gold-colored text.</p>
 */
public enum TextColor {

	BLACK("BLACK", '0', 0),
	DARK_BLUE("DARK_BLUE", '1', 1),
	DARK_GREEN("DARK_GREEN", '2', 2),
	DARK_AQUA("DARK_AQUA", '3', 3),
	DARK_RED("DARK_RED", '4', 4),
	DARK_PURPLE("DARK_PURPLE", '5', 5),
	GOLD("GOLD", '6', 6),
	GRAY("GRAY", '7', 7),
	DARK_GRAY("DARK_GRAY", '8', 8),
	BLUE("BLUE", '9', 9),
	GREEN("GREEN", 'a', 10),
	AQUA("AQUA", 'b', 11),
	RED("RED", 'c', 12),
	LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
	YELLOW("YELLOW", 'e', 14),
	WHITE("WHITE", 'f', 15),
	OBFUSCATED("OBFUSCATED", 'k', true),
	BOLD("BOLD", 'l', true),
	STRIKETHROUGH("STRIKETHROUGH", 'm', true),
	UNDERLINE("UNDERLINE", 'n', true),
	ITALIC("ITALIC", 'o', true),
	RESET("RESET", 'r', -1);

	/**
	 * The name of the color
	 */
	private final String name;

	/**
	 * The color code that will be used.
	 */
	private final char colorCode;

	/**
	 * Used for the formatting codes that aren't just colors, e.g. bold, underline, obfuscated, etc.
	 */
	private final boolean specialRendering;

	/**
	 * The color index for font rendering.
	 */
	private final int index;

	TextColor(String name, char colorCode, boolean specialRendering, int index) {
		this.name = name;
		this.colorCode = colorCode;
		this.specialRendering = specialRendering;
		this.index = index;
	}

	TextColor(String name, char colorCode, int index) {
		this(name, colorCode, false, index);
	}

	TextColor(String name, char colorCode, boolean specialRendering) {
		this(name, colorCode, specialRendering, -1);
	}

	public String getDisplayName() {
		return this.name().toLowerCase(Locale.ROOT);
	}

	@Override
	public String toString() {
		return  "\u00a7" + colorCode;
	}

	public int getIndex() {
		return index;
	}
}
