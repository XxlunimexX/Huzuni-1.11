package net.halalaboos.huzuni.mod.visual.nametags.provider.text;

import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.mod.visual.nametags.provider.TagTextProvider;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;

import java.util.Optional;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

//todo doc
public class PingTextProvider implements TagTextProvider {

	private Toggleable ping;

	public PingTextProvider(Toggleable ping) {
		this.ping = ping;
	}

	/**
	 * Returns the specified Player's ping, used to see their connection to the server.  The ping is displayed in
	 * the format of "{@code # ms}" and colored based on how good or bad their connection is.
	 *
	 * @return The ping
	 */
	@Override
	public String getText(Living entity) {
		Optional<PlayerInfo> playerInfo = getMinecraft().getNetworkHandler().getInfo(entity.getUUID());
		//Check if the playerinfo for this player exists.
		if (playerInfo.isPresent()) {
			//Get the raw ping value
			int ping = playerInfo.get().getPing();
			//Return the formatted ping
			return "" + getColor(ping) + ping + "ms";
		}
		return "";
	}

	private TextColor getColor(int ping) {
		return getFormatted(ping >= 100 && ping < 150, ping >= 150 && ping < 200, ping >= 200);
	}

	@Override
	public boolean isEnabled(Living entity) {
		return ping.isEnabled();
	}
}
