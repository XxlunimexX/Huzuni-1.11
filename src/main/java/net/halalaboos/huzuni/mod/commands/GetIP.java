package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.network.ServerInfo;
import net.minecraft.client.gui.GuiScreen;

import java.util.Optional;

/**
 * @author brudin
 * @version 1.0
 * @since 4/14/14
 */
public final class GetIP extends BasicCommand {

	public GetIP() {
		super("getip", "Copies the server IP to your clipboard.");

	}

	@Override
	protected void runCommand(String input, String[] args) {
		Optional<ServerInfo> serverInfo = MCWrapper.getMinecraft().getServerInfo();
		if (serverInfo.isPresent()) {
			ServerInfo currentServer = serverInfo.get();
			String ip = currentServer.getIP();
			GuiScreen.setClipboardString(ip);
			huzuni.addChatMessage(ip + " copied to your clipboard.");
			return;
		}
		huzuni.addChatMessage("You're not currently connected to a server!");
	}
}
