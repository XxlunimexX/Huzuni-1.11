package net.halalaboos.huzuni.api.gui;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.impl.BasicFontRenderer;

import java.awt.*;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

/**
 * Implementation of the basic font renderer for minecraft. <br/>
 *
 * @since Nov 23, 2013
 */
public final class MinecraftFontRenderer extends BasicFontRenderer {

	private final FontData boldFont = new FontData();

	private final FontData italicFont = new FontData();

	private final FontData boldItalicFont = new FontData();

	private final int[] colorCode = new int[32];

	private final String colorcodeIdentifiers = "0123456789abcdefklmnor";

	public MinecraftFontRenderer() {
		super();
		// Use Minecraft's color code generation.
		for (int index = 0; index < 32; ++index) {
			int noClue = (index >> 3 & 1) * 85;
			int red = (index >> 2 & 1) * 170 + noClue;
			int green = (index >> 1 & 1) * 170 + noClue;
			int blue = (index & 1) * 170 + noClue;

			if (index == 6) {
				red += 85;
			}

			if (index >= 16) {
				red /= 4;
				green /= 4;
				blue /= 4;
			}

			this.colorCode[index] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
		}
	}

    @Override
    public int drawString(String text, int x, int y, int color) {
        return drawString(fontData, text, x, y, color, false);
    }

	public int drawStringWithShadow(String text, int x, int y, int color) {
		return Math.max(drawString(fontData, text, x + 1, y + 1, color, true), drawString(fontData, text, x, y, color, false));
	}

    /**
     * Renders text using the color code rules within the default Minecraft font renderer.
     * */
    public int drawString(FontData fontData, String text, int x, int y, int color, boolean shadow) {
		if (text == null)
			return 0;

		// Trickery used by Minecraft's font renderer.
		if ((color & -67108864) == 0) {
			color |= -16777216;
		}

		// Apply some shadow trickery.
		if (shadow) {
			color = (color & 0xFCFCFC) >> 2 | color & -16777216;
		}

		// Calculate the alpha value first.
        float alpha = (color >> 24 & 0xff) / 255F;

        // If the alpha value is less than 32, default to 255.
        if (alpha <= 32F / 255F)
        	alpha = 1F;

        // Store the original font data given.
        FontData original = fontData;

        // Used to determine which font data to use.
        boolean randomChar = false, bold = false,
        italic = false, strikethrough = false,
        underline = false;
		
        // Multiplied positions since we'll be rendering this at half scale (to look nice!)
        x *= 2F;
        y *= 2F;

        // Prepare GL states.
		getGLStateManager().pushMatrix();
		getGLStateManager().scale(0.5F, 0.5F, 0.5F);
		getGLStateManager().enableAlpha();
		getGLStateManager().enableBlend();
		getGLStateManager().blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		getGLStateManager().color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);

		int size = text.length();
		fontData.bind();
		for (int i = 0; i < size; i++) {
			char character = text.charAt(i);

			// If this character is the special style identifier and the next index is less than the length of the string
			// evaluate which style to switch to.
			if (character == '\247' && i < size && i + 1 < size) {

				// Find the index the next character has within
				int colorIndex = colorcodeIdentifiers.indexOf(text.charAt(i + 1));

				// Switch all styles off, revert to the original font data, and recolor based on the color index.
				if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    randomChar = false;
                    underline = false;
                    strikethrough = false;
            		fontData = original;
					fontData.bind();

   					if (colorIndex < 0 || colorIndex > 15) {
						colorIndex = 15;
					}
   					
					if (shadow) {
						colorIndex += 16;
					}

					// Reset the color.
					getGLStateManager().color((float) (colorCode[colorIndex] >> 16 & 255) / 255.0F, (float) (colorCode[colorIndex] >> 8 & 255) / 255.0F, (float) (colorCode[colorIndex] & 255) / 255.0F, alpha);

   				// Enable random characters.
				} else if (colorIndex == 16) {
                    randomChar = true;

                // Enable bold.
				} else if (colorIndex == 17) {
					bold = true;
					if (italic) {
						fontData = boldItalicFont;
					} else {
						fontData = boldFont;
					}
					fontData.bind();

				// Enable strikethrough.
				} else if (colorIndex == 18) {
					strikethrough = true;

				// Enable underlining.
				} else if (colorIndex == 19) {
					underline = true;

				// Enable italics.
				} else if (colorIndex == 20) { // italic
					italic = true;
					if (bold) {
						fontData = boldItalicFont;
					} else {
						fontData = italicFont;
					}
					fontData.bind();

				// Reset the
				} else if (colorIndex == 21) { // reset
                    bold = false;
                    italic = false;
                    randomChar = false;
                    underline = false;
                    strikethrough = false;
					getGLStateManager().color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, alpha);
                    fontData = original;
					fontData.bind();
                }

                // Increase the index, since it is necessary to not render the style identifier as well as the style type.
				i ++;
			} else {
				if (fontData.hasBounds(character)) {
					if (randomChar) {
						char newChar = 0;
						// Randomly find a character with the same width as the actual character which is supposed to render.
						// This is a terrible thing to do. Don't do it.
						while (fontData.getCharacterBounds(newChar).width != fontData.getCharacterBounds(character).width)
							newChar = (char) (Math.random() * 256);
						character = newChar;
					}
					FontData.CharacterData area = fontData.getCharacterBounds(character);

					GLUtils.drawTextureRect(x, y, area.width, area.height,
							(float) area.x / fontData.getImage().getWidth(),
							(float) area.y / fontData.getImage().getHeight(),
							(float) (area.x + area.width) / fontData.getImage().getWidth(),
							(float) (area.y + area.height) / fontData.getImage().getHeight());

					if (strikethrough)
						GLUtils.drawLine(1F, x, y + area.height / 4 + 2, x + area.width / 2, y + area.height / 4 + 2);

					if (underline)
						GLUtils.drawLine(1F, x, y + area.height / 2, x + area.width / 2, y + area.height / 2);
					x += area.width + kerning;
				}
			}
		}
		getGLStateManager().popMatrix();
		return x;
	}

	/**
     * @return The height of the text which will be outputted by this font renderer. <br/>
     * This information can normally be acquired through the {@link FontData} object, but with the MinecraftFontRenderer, multiple {@link FontData}s may be used.
     * */
	public int getStringHeight(String text) {
		if (text == null)
			return 0;
		int height = 0;
		FontData currentFont = fontData;
        boolean bold = false, italic = false;
		int size = text.length();
		
		for (int i = 0; i < size; i++) {
			char character = text.charAt(i);
			if (character == '\247' && i < size) {
				int colorIndex = colorcodeIdentifiers.indexOf(character);
				if (colorIndex < 16) { // coloring
					bold = false;
					italic = false;
				} else if (colorIndex == 17) { // bold
					bold = true;
					if (italic)
						currentFont = boldItalicFont;
					else
						currentFont = boldFont;
				} else if (colorIndex == 20) { // italic
					italic = true;
					if (bold)
						currentFont = boldItalicFont;
					else
						currentFont = italicFont;
				} else if (colorIndex == 21) { // reset
					bold = false;
					italic = false;
					currentFont = fontData;
				}
				i++;
			} else {
				if (currentFont.hasBounds(character)) {
					if (currentFont.getCharacterBounds(character).height > height)
						height = currentFont.getCharacterBounds(character).height;
				}
			}
		}
        return height / 2;
	}

	/**
     * @return The width of the text which will be outputted by this font renderer. <br/>
     * This information can normally be acquired through the {@link FontData} object, but with the MinecraftFontRenderer, multiple {@link FontData}s may be used.
     * */
	public int getStringWidth(String text) {
		if (text == null)
			return 0;
		int width = 0;
		FontData currentFont = fontData;
        boolean bold = false, italic = false;
		int size = text.length();
		
		for (int i = 0; i < size; i++) {
			char character = text.charAt(i);
			if (character == '\247' && i < size) {
				int colorIndex = colorcodeIdentifiers.indexOf(character);
				if (colorIndex < 16) { // coloring
					bold = false;
					italic = false;
				} else if (colorIndex == 17) { // bold
					bold = true;
					if (italic)
						currentFont = boldItalicFont;
					else
						currentFont = boldFont;
				} else if (colorIndex == 20) { // italic
					italic = true;
					if (bold)
						currentFont = boldItalicFont;
					else
						currentFont = italicFont;
				} else if (colorIndex == 21) { // reset
					bold = false;
					italic = false;
					currentFont = fontData;
				}
				i++;
			} else {
				if (currentFont.hasBounds(character)) {
					width += currentFont.getCharacterBounds(character).width + kerning;
				}
			}
		}
        return width / 2;
	}

	/**
	 * Applies a new font to the default font data as well as the bold, italic, and the bolditalic font data.
	 * */
	public void setFont(Font font, boolean antialias) {
		this.fontData.setFont(font, antialias);
		this.boldFont.setFont(font.deriveFont(Font.BOLD), antialias);
		this.italicFont.setFont(font.deriveFont(Font.ITALIC), antialias);
		this.boldItalicFont.setFont(font.deriveFont(Font.BOLD | Font.ITALIC), antialias);
	}
}
