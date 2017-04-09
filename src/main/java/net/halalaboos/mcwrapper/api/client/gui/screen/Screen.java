package net.halalaboos.mcwrapper.api.client.gui.screen;

import net.halalaboos.mcwrapper.api.MCWrapper;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

public class Screen {

	private List<Button> buttons = new ArrayList<>();
	private boolean panorama;

	public void addButton(Button button) {
		this.buttons.add(button);
	}

	public void setPanorama(boolean panorama) {
		this.panorama = panorama;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {}

	protected void mouseReleased(int mouseX, int mouseY, int state) {}

	public void handleMouseInput() throws IOException {}

	public void updateScreen() {}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected int getWidth(boolean scaled) {
		return scaled ? getMinecraft().getScreenResolution().scaledWidth : getMinecraft().getScreenResolution().width;
	}

	protected int getHeight(boolean scaled) {
		return scaled ? getMinecraft().getScreenResolution().scaledHeight : getMinecraft().getScreenResolution().height;
	}
}
