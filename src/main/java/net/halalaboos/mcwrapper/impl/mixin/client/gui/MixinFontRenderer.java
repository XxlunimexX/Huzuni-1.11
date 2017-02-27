package net.halalaboos.mcwrapper.impl.mixin.client.gui;

import net.halalaboos.mcwrapper.api.client.gui.TextRenderer;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer implements TextRenderer {

	@Shadow public abstract int drawStringWithShadow(String text, float x, float y, int color);
	@Shadow public abstract int drawString(String text, int x, int y, int color);
	@Shadow public int FONT_HEIGHT;
	@Shadow public abstract int getStringWidth(String text);

	@Override
	public void render(String text, int x, int y, int color, boolean shadow) {
		if (shadow) {
			drawStringWithShadow(text, x, y, color);
		} else {
			drawString(text, x, y, color);
		}
	}

	@Override
	public void render(String text, float x, float y, int color) {
		drawStringWithShadow(text, x, y, color);
	}

	@Override
	public int getHeight() {
		return FONT_HEIGHT;
	}

	@Override
	public int getWidth(String text) {
		return getStringWidth(text);
	}
}
