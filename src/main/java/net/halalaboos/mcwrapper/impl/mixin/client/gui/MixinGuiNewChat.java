package net.halalaboos.mcwrapper.impl.mixin.client.gui;

import net.halalaboos.huzuni.Huzuni;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {

	private final Huzuni huzuni = Huzuni.INSTANCE;

	@Redirect(method = "drawChat", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
	public int changeFontRenderer(FontRenderer fontRenderer, String text, float x, float y, int color) {
		if (huzuni.settings.customChat.isEnabled()) {
			huzuni.chatFontRenderer.drawStringWithShadow(text, (int) x, (int) y - 3, color);
		} else {
			fontRenderer.drawStringWithShadow(text, x, y, color);
		}
		return 0;
	}
}
