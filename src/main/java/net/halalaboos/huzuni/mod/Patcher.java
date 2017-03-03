package net.halalaboos.huzuni.mod;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.huzuni.mod.movement.Flight;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.mcwrapper.api.event.MouseEvent;
import net.halalaboos.mcwrapper.api.util.MouseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.util.math.RayTraceResult;

import static net.halalaboos.mcwrapper.api.MCWrapper.getEventManager;

/**
 * @since 5:05 PM on 3/21/2015
 * @author Brendan
 */
public class Patcher {

	private boolean shouldHideFlying = true;

	/**
	 * PATCHER isn't really a mod, but moreso a way to prevent the client from sending things that
	 * would make it clear if the user is hacking. Because of this, it can only be disabled when huzuni is.
	 *
	 * TODO:
	 * 	prevent client from sending server fly state (ez)
	 */

	public Patcher() {}

	public void init() {
		Huzuni.INSTANCE.eventManager.addListener(this);
		getEventManager().subscribe(MouseEvent.class, event -> {
			if (event.getButton() == MouseButton.MIDDLE) {
				if (Minecraft.getMinecraft().objectMouseOver != null) {
					RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
					if (result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof EntityPlayer) {
						if (Huzuni.INSTANCE.friendManager.isFriend(result.entityHit.getName())) {
							Huzuni.INSTANCE.addChatMessage(String.format("Removed %s as a friend.", result.entityHit.getName()));
							Huzuni.INSTANCE.friendManager.removeFriend(result.entityHit.getName());
							Huzuni.INSTANCE.friendManager.save();
						} else {
							Huzuni.INSTANCE.friendManager.addFriend(result.entityHit.getName());
							Huzuni.INSTANCE.addChatMessage(String.format("Added %s as a friend.", result.entityHit.getName()));
							Huzuni.INSTANCE.friendManager.save();
						}
					}
				}
			}
		});
	}

	@EventMethod
	public void onPacket(PacketEvent event) {
		if (event.type == PacketEvent.Type.READ) {
			if (event.getPacket() instanceof SPacketPlayerAbilities) {
				SPacketPlayerAbilities packet = (SPacketPlayerAbilities) event.getPacket();
				shouldHideFlying = !(packet.isAllowFlying() || packet.isFlying());
			}
		}
		if (event.type == PacketEvent.Type.SENT) {
			if (event.getPacket() instanceof CPacketTabComplete) {
				event.setPacket(hideCommands((CPacketTabComplete) event.getPacket()));
			}
			
			if (event.getPacket() instanceof CPacketPlayerAbilities) {
				event.setPacket(removeFlying((CPacketPlayerAbilities) event.getPacket()));
			}
		}
	}

	private CPacketPlayerAbilities removeFlying(CPacketPlayerAbilities packet) {
		if ((Freecam.INSTANCE.isEnabled() || Flight.INSTANCE.isEnabled()) && shouldHideFlying) {
			packet.setFlying(false);
		}
		return packet;
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
	 * @param packet	The tab complete packet to modify
	 * @return			A new tab complete packet!
	 */
	private CPacketTabComplete hideCommands(CPacketTabComplete packet) {
		String packetOutput = packet.getMessage();
		if (!packetOutput.startsWith(Huzuni.INSTANCE.commandManager.getCommandPrefix()))
			return packet;
		String[] packetOutputArray = packetOutput.split(" ");
		String toSend = packetOutputArray[packetOutputArray.length - 1];
		if (toSend.startsWith(Huzuni.INSTANCE.commandManager.getCommandPrefix())) {
			toSend = toSend.substring(1, toSend.length());
		}
		Huzuni.INSTANCE.addNotification(NotificationType.INFO, "Patcher", 5000, "Hiding tab information!");
		return new CPacketTabComplete(toSend, packet.getTargetBlock(), packet.hasTargetBlock());
	}
}
