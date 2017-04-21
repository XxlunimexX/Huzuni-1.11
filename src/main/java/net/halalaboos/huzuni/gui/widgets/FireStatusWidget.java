package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class FireStatusWidget extends BackgroundWidget {

	public FireStatusWidget(WidgetManager menuManager) {
		super("Fire Status", "Useful if you have fire overlay disabled with 'No effect'.", menuManager);
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
		String BURNING_TEXT = "On Fire!";
		if (getPlayer().isOnFire()) {
			theme.drawString(BURNING_TEXT, x, y, -1);
		}
		setWidth(theme.getStringWidth(BURNING_TEXT));
	}
}
