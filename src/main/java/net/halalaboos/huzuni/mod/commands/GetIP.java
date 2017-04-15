package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.mcwrapper.api.network.ServerInfo;
import net.halalaboos.mcwrapper.api.util.SystemUtils;

import java.util.Optional;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * @author brudin
 * @since 4/14/14
 */
public final class GetIP extends BasicCommand {

	public GetIP() {
		super("getip", "Copies the server IP to your clipboard.");
	}

	@Override
	protected void runCommand(String input, String[] args) {
		Optional<ServerInfo> serverInfo = getMinecraft().getServerInfo();
		if (serverInfo.isPresent()) {
			ServerInfo currentServer = serverInfo.get();
			String ip = currentServer.getIP();
			SystemUtils.copyToClipboard(ip);
			huzuni.addChatMessage(ip + " copied to your clipboard.");
			return;
		}
		huzuni.addChatMessage("You're not currently connected to a server!");
	}
}
