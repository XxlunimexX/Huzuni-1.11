package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;

import java.awt.*;

/**
 * Renders the names of players within a given range.
 * */
public class TextRadarWidget extends BackgroundWidget {

	private final Value distance = new Value("Distance", 10F, 130F, 255F, 5F, "Distance required for entities to be rendered.");
	private final Value opacity = new Value("Opacity", "%", 10F, 100F, 100F, 1F, "Opacity/transparency of the text.");
	private final Value maxPlayers = new Value("Maximum Players", 1F, 100F, 100F, 1F, "Maximum amount of players to list on the radar.");

	public TextRadarWidget(WidgetManager menuManager) {
		super("Text Radar", "Render an old-school text radar", menuManager);
		this.addChildren(distance, opacity, maxPlayers);
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
		int incrementOffset = getIncrementOffset(), originalWidth = width;
		height = 0;
		width = 0;
		if (incrementOffset == -1)
			y = y + height - theme.getStringHeight("minimum");
		ClientPlayer me = MCWrapper.getPlayer();
		Color textColor = new Color(255, 255, 255, (int)((opacity.getValue() / 100) * 255));
		int playerCount = 0;
		for (Player player : MCWrapper.getWorld().getPlayers()) {
			if (player != me && !player.isNPC()) {
				double distance = me.getDistanceTo(player);
				if (distance < this.distance.getValue()) {
					playerCount++;
					if (playerCount <= maxPlayers.getValue()) {
						String text = String.format("%s (%d)", player.name(), (int) distance);
						int textWidth = theme.getStringWidth(text);
						theme.drawStringWithShadow(text, getOffsetX(x, x + originalWidth, textWidth), y, textColor.getRGB());
						height += theme.getStringHeight(text);
						y += incrementOffset * theme.getStringHeight(text);
						if (textWidth + 2 > width) {
							width = textWidth + 2;
						}
					}
				}
			}
		}
		this.setWidth(width);
		this.setHeight(height <= 0 ? 10 : height);
	}
	
	private int getOffsetX(int x, int x1, int itemWidth) {
		return glue.isRight() ? x1 - itemWidth - 2 : x;
	}
	
	private int getIncrementOffset() {
		return glue.isBottom() ? -1 : 1;
	}

}
