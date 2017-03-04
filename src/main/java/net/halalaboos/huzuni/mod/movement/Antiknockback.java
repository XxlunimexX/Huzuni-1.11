package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Toggleable;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.mcwrapper.api.network.packet.client.UseEntityPacket;
import net.halalaboos.mcwrapper.api.network.packet.server.EntityVelocityPacket;
import net.halalaboos.mcwrapper.api.network.packet.server.ExplosionPacket;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Prevents the Player from being knocked back in different scenarios.
 */
public class Antiknockback extends BasicMod {

	/** A timer that is reset every time we attack an Entity if Combat Mode is enabled. */
	private final Timer timer = new Timer();

	/** Toggle that prevents knockback only in combat */
	private final Toggleable combat = new Toggleable("Combat mode", "Prevents knockback when only in combat");

	/** The ignored knockback percentage */
    private final Value percentage = new Value("Percentage", "%", 0F, 80F, 100F, 5F, "Percentage of knockback that will be ignored.");

	/** The max time after attacking before the mod decides we aren't in combat */
    private final Value combatTime = new Value("Combat time", " ms", 1000F, 3000F, 10000F, 10F, "Time required to pass until no longer considered in combat");

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
			//Reset the combat timer when we attack an entity since we are in combat now
			if (packet.getUseAction() == UseEntityPacket.UseAction.ATTACK) {
				timer.reset();
			}
		} else if (event.getPacket() instanceof EntityVelocityPacket) {
			EntityVelocityPacket packet = (EntityVelocityPacket) event.getPacket();
			//Check if the Packet directed at the Player
			if (packet.getId() == getPlayer().getId()) {
				if (!combat.isEnabled() || !timer.hasReach((int) combatTime.getValue())) {
					//Don't bother doing any calculations since we are going to be removing the knockback entirely
					if (percentage.getValue() == 1F) {
						event.setCancelled(true);
					} else {
						//Set the velocity based on the percentage value
						packet.setVelocity(adjustVelocity(packet.getVelocity(), 1F - (percentage.getValue() / 100F)));
					}
				}
			}
		} else if (event.getPacket() instanceof ExplosionPacket) {
			//Avoid being knocked back by Explosions
			event.setCancelled(true);
		}
	}

	/**
	 * Adjusts the given Vector's {@link Vector3d#x}, {@link Vector3d#y}, and {@link Vector3d#z} points based on
	 * the given percentage.  This is made for the {@link EntityVelocityPacket} and shouldn't be used for anything else.
	 *
	 * @param velocity The input velocity
	 * @param percent The percentage
	 * @return The new Vector with adjusted velocity
	 */
	private Vector3d adjustVelocity(Vector3d velocity, float percent) {
		return new Vector3d(percent * (velocity.getX() / 8000), percent * (velocity.getY() / 8000),
				percent * (velocity.getZ() / 8000));
	}
}
