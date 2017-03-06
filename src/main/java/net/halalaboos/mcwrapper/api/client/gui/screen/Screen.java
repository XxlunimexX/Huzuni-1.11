package net.halalaboos.mcwrapper.api.client.gui.screen;

import java.util.ArrayList;
import java.util.List;

public class Screen {

	private List<Button> buttons = new ArrayList<>();
	private boolean panorama;

	public Screen addButton(Button button) {
		this.buttons.add(button);
		return this;
	}

	public Screen setPanorama(boolean panorama) {
		this.panorama = panorama;
		return this;
	}

	public List<Button> getButtons() {
		return buttons;
	}
}
