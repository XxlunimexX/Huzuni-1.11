package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;
import net.halalaboos.huzuni.api.node.impl.Toggleable;

import java.text.DecimalFormat;

import static net.halalaboos.huzuni.api.util.gl.GLUtils.getScreenWidth;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Widget which displays the player's coordinates.
 * */
public class CoordinatesWidget extends BackgroundWidget {

	private Toggleable multiLine = new Toggleable("Multi-line", "Render each coordinate on a new line.");

	public CoordinatesWidget(WidgetManager menuManager) {
		super("Coordinates", "Render players coordinates", menuManager);
		addChildren(multiLine);
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
		if (multiLine.isEnabled()) {
			renderMultiLine(x, y);
		} else {
			String coordinates = getCoordinates();
			theme.drawStringWithShadow(coordinates, x, y, 0xFFFFFF);
			this.setWidth(theme.getStringWidth(coordinates) + 2);
			this.setHeight(theme.getStringHeight(coordinates));
		}
	}

	private void renderMultiLine(int x, int y) {
		String[] lines = getMultilineCoords();
		int addedY = 0;
		for (String coordinate : lines) {
			int lineWidth = theme.getStringWidth(coordinate);
			int renderX = glue.isRight() ? getScreenWidth() - lineWidth - 2 : glue.isCenterX() ? getScreenWidth() / 2 - lineWidth / 2 : x;
			theme.drawStringWithShadow(coordinate, renderX, y + addedY, -1);
			addedY += theme.getStringHeight(coordinate) + 1;
		}
		setHeight(addedY);
		setWidth(getLongest(lines));
	}

	private int getLongest(String[] in) {
		int out = 2;
		for (String s : in) {
			if (theme.getStringWidth(s) > out) {
				out = theme.getStringWidth(s);
			}
		}
		return out;
	}

	private String[] getMultilineCoords() {
		DecimalFormat df = new DecimalFormat("0.0");
		String x = "X: " + df.format(getPlayer().getX());
		String y = "Y: " + df.format(getPlayer().getY());
		String z = "Z: " + df.format(getPlayer().getZ());
		return new String[] {x,y,z};
	}

	private String getCoordinates() {
		return getPlayer().getCoordinates();
	}
}
