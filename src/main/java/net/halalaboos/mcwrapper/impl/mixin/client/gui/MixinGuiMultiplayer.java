package net.halalaboos.mcwrapper.impl.mixin.client.gui;

import net.halalaboos.huzuni.gui.screen.account.AccountScreen;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {
	/**
	 * Adds the accounts button.
	 */
	@Inject(method = "initGui", at = @At("RETURN"))
	public void setup(CallbackInfo ci) {
		this.buttonList.add(new GuiButton(400, 5, 5, 100, 20, "Accounts"));
	}

	/**
	 * Makes the accounts button usable.
	 */
	@Inject(method = "actionPerformed", at = @At("HEAD"))
	public void onClick(GuiButton button, CallbackInfo ci) throws IOException {
		if (button.id == 400) {
			MCWrapper.getMinecraft().showScreen(new AccountScreen());
		}
	}
}
