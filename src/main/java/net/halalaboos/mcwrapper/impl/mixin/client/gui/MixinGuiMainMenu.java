package net.halalaboos.mcwrapper.impl.mixin.client.gui;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.util.gl.Texture;
import net.halalaboos.huzuni.gui.screen.HuzuniLink;
import net.halalaboos.huzuni.render.ParticleRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;

/**
 * Incredibly hacky way of making the Minecraft main menu look like our Huzuni one while still supporting other mods'
 * changes to the main menu.
 *
 * With that said, it could very well cause some issues for mods that have bigger changes to the menu... Whatever!
 */
@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

	//Setup particle renderer
	private final ParticleRenderer particleRenderer = new ParticleRenderer(width, height);

	//Load title texture
	private final Texture TITLE = new Texture("title.png");

	/**
	 * Adds the update button.
	 *
	 * TODO: Add accounts button (IMPORTANT!!)
	 */
	@Inject(method = "initGui", at = @At("RETURN"))
	public void setup(CallbackInfo ci) {
		//Set the size of the particle renderer
		particleRenderer.updateSize(width, height);
		String updateText = "A new Huzuni update is available! Click me to download.";
		if (Huzuni.INSTANCE.settings.hasUpdate()) {
			//Add update button if necessary
			this.buttonList.add(new HuzuniLink(400, 5, 5, fontRenderer.getStringWidth(updateText), 15, updateText));
		}
	}

	/**
	 * Makes the update button usable.
	 */
	@Inject(method = "actionPerformed", at = @At("HEAD"))
	public void onClick(GuiButton button, CallbackInfo ci) throws IOException {
		if (button.id == 400) {
			try {
				Desktop.getDesktop().browse(new URL("http://halalaboos.net").toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Updates the particles.
	 */
	@Inject(method = "updateScreen", at = @At("HEAD"))
	public void updatePanorama(CallbackInfo ci) {
		particleRenderer.updateParticles();
	}

	/**
	 * Renders the particles.
	 */
	@Inject(method = "drawScreen", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiMainMenu;renderSkybox(IIF)V", shift = At.Shift.AFTER))
	public void replacePanoramaRenderer(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
		particleRenderer.renderParticles();
	}

	/**
	 * Renders the Huzuni version on the top right of the screen.
	 */
	@Inject(method = "drawScreen", at = @At(value = "RETURN"))
	public void renderHuzuniVersion(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		this.drawString(fontRenderer, Huzuni.VERSION, width - fontRenderer.getStringWidth(Huzuni.VERSION) - 2, 2, 0x3FFFFFFF);
	}

	/**
	 * Removes the gradients from rendering on the menu, since we already got that covered.
	 */
	@Redirect(method = "drawScreen", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V"))
	public void removeGradients(GuiMainMenu guiMainMenu, int left, int top, int right,
								int bottom, int startColor, int endColor) {}

	/**
	 * Renders the Huzuni logo.
	 */
	@Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
	public void removeMenuLogoInit(TextureManager textureManager, ResourceLocation resource) {
		float titleX = width / 2 - 150, titleY = 20;
		getGLStateManager().color(1.0F, 1.0F, 1.0F, 1.0F);
		TITLE.render(titleX, titleY + 10, 300, 100);
	}

	/**
	 * Prevents Minecraft's logo from rendering by replacing the drawTextureModalRect methods in drawScreen to
	 * nothing.
	 *
	 * Since drawTexturedModalRect is only used for the logo, this doesn't break anything in the vanilla menu.
	 */
	@Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawTexturedModalRect(IIIIII)V"))
	public void removeMenuLogoRendering(GuiMainMenu guiMainMenu, int x, int y, int textureX, int textureY, int width, int height) {}

	/**
	 * Changes the color of text to a more transparent and eye-pleasing color.
	 *
	 * This does not change the color for Forge's update alerts, which at first was a bug but after some thinking, if it
	 * annoys you, just update Forge!
	 */
	@Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V"))
	public void changeTextColor(GuiMainMenu guiMainMenu, FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawStringWithShadow(text, x, y, 0x3FFFFFFF);
	}
}
