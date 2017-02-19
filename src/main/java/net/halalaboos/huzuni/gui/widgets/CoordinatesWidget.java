package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;
import net.halalaboos.mcwrapper.api.Tupac;

/**
 * Widget which displays the player's coordinates.
 * */
public class CoordinatesWidget extends BackgroundWidget {

	public CoordinatesWidget(WidgetManager menuManager) {
		super("Coordinates", "Render players coordinates", menuManager);
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
		String coordinates = String.format("(%s)", Tupac.getPlayer().getCoordinates());
		theme.drawStringWithShadow(coordinates, x, y, 0xFFFFFF);
		this.setWidth(theme.getStringWidth(coordinates) + 2);
		this.setHeight(theme.getStringHeight(coordinates));
	}
}
