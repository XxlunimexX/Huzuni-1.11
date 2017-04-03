package net.halalaboos.huzuni.mod.visual.nametags.provider;

import net.halalaboos.huzuni.mod.visual.nametags.Nametags;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

/**
 * Various options for {@link Nametags} only add text, such as the health or ping options.  These options clutter
 * up the code for the mod a bit and also make things a but less organized.  This is meant to be used for cases
 * like those, so we would have a HealthTextProvider, etc.
 *
 * <p>NOTE: There should only be <strong>one</strong> instance of a text provider.  Don't create a new one
 * for each Entity, this is a bad idea - you don't want to create hundreds of instances of a provider every tick
 * when you're in the lobby of a large server or something.</p>
 *
 * TODO: Right now each provider is called directly in the {@link Nametags#setupAndRender(Player, Vector3d)} method.
 */
public interface TagTextProvider extends TagProvider {

	/**
	 * The text this provider will add to the Nameplate.
	 * @param entity The entity the Nameplate is for.
	 */
	String getText(Living entity);

	/**
	 * When rendering information such as ping or health, the text will be colored using this method.  If none of the
	 * conditions are met, then it will return {@link TextColor#GREEN}.
	 *
	 * @param yellow If the color should be yellow
	 * @param orange If the color should be gold/orange
	 * @param red If the color should be red
	 *
	 * @return The color
	 */
	default TextColor getFormatted(boolean yellow, boolean orange, boolean red) {
		return yellow ? TextColor.YELLOW : orange ? TextColor.GOLD : red ? TextColor.RED : TextColor.GREEN;
	}
}
