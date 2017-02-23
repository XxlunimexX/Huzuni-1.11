package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Toggleable;
import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.mcwrapper.api.network.packet.client.UseEntityPacket;
import net.halalaboos.mcwrapper.api.network.packet.server.EntityVelocityPacket;
import net.halalaboos.mcwrapper.api.network.packet.server.ExplosionPacket;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Prevents the player from receiving knockback.
 * */
public class Antiknockback extends BasicMod {
	
	private final Timer timer = new Timer();
	
	public final Toggleable combat = new Toggleable("Combat mode", "Prevents knockback when only in combat");

    public final Value percentage = new Value("Percentage", "%", 0F, 80F, 100F, 5F, "Percentage of knockback that will be ignored.");

    public final Value combatTime = new Value("Combat time", " ms", 1000F, 3000F, 10000F, 10F, "Time required to pass until no longer considered in combat");

	public Antiknockback() {
		super("Anti knockback", "Removes a percentage from the knockback velocity");
		this.addChildren(combat, combatTime, percentage);
		this.setCategory(Category.MOVEMENT);
		setAuthor("brudin");
	}
	
	@Override
	public void onEnable() {
		huzuni.eventManager.addListener(this);
	}
	
	@Override
	public void onDisable() {
		huzuni.eventManager.removeListener(this);
	}

	@EventMethod
	public void onPacket(PacketEvent event) {
		if (event.getPacket() instanceof UseEntityPacket) {
			UseEntityPacket packet = (UseEntityPacket) event.getPacket();
			if (packet.getUseAction() == UseEntityPacket.UseAction.ATTACK) {
				timer.reset();
			}
		} else if (event.getPacket() instanceof EntityVelocityPacket) {
			EntityVelocityPacket packet = (EntityVelocityPacket) event.getPacket();
			if (packet.getId() == getPlayer().getId()) {
				if (!combat.isEnabled() || !timer.hasReach((int) combatTime.getValue())) {
					if (percentage.getValue() == 1F) {
						event.setCancelled(true);
					} else {
						float percent = 1F - (percentage.getValue() / 100F);
						double motionX = percent * (packet.getVelocity().getX() / 8000);
						double motionY = percent * (packet.getVelocity().getY() / 8000);
						double motionZ = percent * (packet.getVelocity().getZ() / 8000);
						packet.setVelocity(new Vector3d(motionX, motionY, motionZ));
					}
				}
			}
		} else if (event.getPacket() instanceof ExplosionPacket) {
			event.setCancelled(true);
		}
	}

}
