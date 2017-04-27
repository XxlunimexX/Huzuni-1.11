package net.halalaboos.huzuni.mod;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.huzuni.mod.movement.Flight;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.event.input.MouseEvent;
import net.halalaboos.mcwrapper.api.event.network.PacketReadEvent;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.network.packet.client.PlayerAbilitiesPacket;
import net.halalaboos.mcwrapper.api.network.packet.client.TabCompletePacket;
import net.halalaboos.mcwrapper.api.network.packet.server.ServerPlayerAbilitiesPacket;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;
import net.halalaboos.mcwrapper.api.util.math.Result;

import static net.halalaboos.mcwrapper.api.MCWrapper.getEventManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * @since 5:05 PM on 3/21/2015
 * @author Brendan
 */
public class Patcher {

	private boolean shouldHideFlying = true;

	private final Huzuni huzuni;

	/**
	 * PATCHER isn't really a mod, but moreso a way to prevent the client from sending things that
	 * would make it clear if the user is hacking. Because of this, it can only be disabled when huzuni is.
	 *
	 * TODO:
	 * 	prevent client from sending server fly state (ez)
	 */

	public Patcher(Huzuni huzuni) {
		this.huzuni = huzuni;
	}

	public void init() {
		getEventManager().subscribe(MouseEvent.class, event -> {
			if (event.getButton() == MouseButton.MIDDLE) {
				if (getMinecraft().getMouseResult().isPresent() && getMinecraft().getMousedEntity().isPresent()) {
					Result result = getMinecraft().getMouseResult().get();
					Entity entity = getMinecraft().getMousedEntity().get();
					if (result == Result.ENTITY && entity instanceof Player) {
						if (huzuni.friendManager.isFriend(entity.name())) {
							huzuni.addChatMessage(String.format("Removed %s as a friend.", entity.name()));
							huzuni.friendManager.removeFriend(entity.name());
							huzuni.friendManager.save();
						} else {
							huzuni.friendManager.addFriend(entity.name());
							huzuni.addChatMessage(String.format("Added %s as a friend.", entity.name()));
							huzuni.friendManager.save();
						}
					}
				}
			}
		});
		getEventManager().subscribe(PacketReadEvent.class, event -> {
			if (event.getPacket() instanceof ServerPlayerAbilitiesPacket) {
				ServerPlayerAbilitiesPacket packet = (ServerPlayerAbilitiesPacket) event.getPacket();
				shouldHideFlying = !(packet.isFlyingAllowed() || packet.isFlying());
			}
		});
		getEventManager().subscribe(PacketSendEvent.class, event -> {
			if (event.getPacket() instanceof TabCompletePacket) {
				TabCompletePacket packet = (TabCompletePacket) event.getPacket();
				packet.setText(hideCommands(packet.getText()));
			}
			if (event.getPacket() instanceof PlayerAbilitiesPacket) {
				PlayerAbilitiesPacket packet = (PlayerAbilitiesPacket) event.getPacket();
				// TODO: Stop referencing to these mods statically.
				if ((Freecam.INSTANCE.isEnabled() || Flight.INSTANCE.isEnabled()) && shouldHideFlying) {
					packet.setFlying(false);
				}
			}
		});
		// TODO: Figure out why this causes singleplayer to freeze before loading the world.
		/*getEventManager().subscribe(WorldLoadEvent.class, event -> {
			if (event.getWorld() != null) {
				if (huzuni.settings.firstUse.isEnabled()) {
					huzuni.addChatMessage("Welcome to huzuni!");
					huzuni.addChatMessage("Press right shift to open up the settings menu!");
					huzuni.addChatMessage("Type \".help\" for a list of commands!");
					huzuni.settings.firstUse.setEnabled(false);
				}
			}
			huzuni.lookManager.cancelTask();
		});*/
	}

	/**
	 * When you tab complete a message, it will send the server the entire message.  This means that servers will
	 * be able to tell if someone does .add [playername] [alias] or whatever because the client is literally telling
	 * the server that when autocompleting the player names.
	 * What this does is only sends the last part of the message if it starts with a '.'.  So in the case of:
	 *		.add b[TABCOMPLETE]
	 * Instead of sending that entire message, it will only be sending:
	 * 		b
	 * Fun!
	 */
	private String hideCommands(String input) {
		if (!input.startsWith(Huzuni.INSTANCE.commandManager.getCommandPrefix()))
			return input;
		String[] packetOutputArray = input.split(" ");
		String toSend = packetOutputArray[packetOutputArray.length - 1];
		if (toSend.startsWith(Huzuni.INSTANCE.commandManager.getCommandPrefix())) {
			toSend = toSend.substring(1, toSend.length());
		}
		Huzuni.INSTANCE.addNotification(NotificationType.INFO, "Patcher", 5000, "Hiding tab information!");
		return toSend;
	}
}
