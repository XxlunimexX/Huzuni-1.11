package net.halalaboos.mcwrapper.impl.guiscreen;

import net.halalaboos.mcwrapper.api.client.gui.screen.Button;
import net.halalaboos.mcwrapper.api.client.gui.screen.Screen;
import net.halalaboos.mcwrapper.api.util.MouseButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GuiScreenWrapper extends GuiScreen {

	private Screen screen;
	private Map<GuiButton, Button> buttonMap = new HashMap<>();

	public GuiScreenWrapper(Screen screen) {
		this.screen = screen;
		for (Button button : screen.getButtons()) {
			this.buttonMap.put(new GuiButton(button.getId(), button.getX(), button.getY(), button.getWidth(), button.getHeight(),
					button.getText()), button);
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		for (GuiButton button : buttonMap.keySet()) {
			addButton(button);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for (GuiButton button : buttonMap.keySet()) {
			if (button.isMouseOver()) {
				buttonMap.get(button).getOnClick().apply(buttonMap.get(button), MouseButton.getMouseButton(mouseButton));
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
