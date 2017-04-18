package net.halalaboos.mcwrapper.api.client.gui.screen;

import net.halalaboos.huzuni.Huzuni;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Screen {

	protected Huzuni huzuni = Huzuni.INSTANCE;

	private List<Button> buttons = new ArrayList<>();

	public int width, height;

	private boolean panorama;

	public void addButton(Button button) {
		this.buttons.add(button);
	}

	public void setPanorama(boolean panorama) {
		this.panorama = panorama;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}

	public void keyTyped(char typedChar, int keyCode) throws IOException {}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {}

	public void mouseReleased(int mouseX, int mouseY, int state) {}

	public void handleMouseInput() throws IOException {}

	public void initGui() {}

	public void updateScreen() {}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void drawBackground() {
		background = true;
	}

	public boolean shouldDrawBackground() {
		return background;
	}

	private boolean background = false;

	public List<Button> getButtons() {
		return buttons;
	}

	public boolean isPanorama() {
		return panorama;
	}
}
