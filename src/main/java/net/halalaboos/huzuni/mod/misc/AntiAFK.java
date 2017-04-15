package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

/**
 * Some servers will detect if a player hasn't moved for x seconds/minutes and will auto-kick them to avoid them from
 * hogging spots for other players.  This mod attempts to prevent this from happening, by allowing the player to have
 * the client automatically jump for them at a set delay when it detects that they are not moving.
 *
 * What this means is that you could probably leave this on while playing, since it'll only jump if you haven't moved
 * in the 'wait' period.  Probably would be a little annoying, though!
 *
 * @author b
 */
public class AntiAFK extends BasicMod {

	/**
	 * This is used to check if 'x' seconds have passed since the last time we jumped (or moved)
	 */
	private net.halalaboos.huzuni.api.util.Timer timer = new Timer();

	/**
	 * This is used to detect whether or not we have moved.  Another approach would be to have all of this done
	 * under the {@link net.halalaboos.mcwrapper.api.event.player.MoveEvent} and check if the player's motionXYZ changed
	 * at all.
	 */
	private Vector3d lastLocation = Vector3d.ZERO;

	/**
	 * The delay (in seconds) to check for movement changes.
	 */
	private Value delay = new Value("Delay", " seconds", 1, 5, 15, 1,"How long to wait before jumping");

	public AntiAFK() {
		super("Anti AFK", "Prevents you from getting kicked for not moving.");
		setAuthor("brudin");
		addChildren(delay);
		setCategory(Category.MISC);
		subscribe(PreMotionUpdateEvent.class, event -> {
			//Check if our position has changed at all.
			if (!this.lastLocation.equals(mc.getPlayer().getLocation())) {
				//Reset the last location to the player's current location
				this.lastLocation = mc.getPlayer().getLocation();
				//Reset the timer, since we don't need to jump given we have already moved.
				timer.reset();
			}

			//Once the timer has reached x seconds, we will jump if the player isn't already in the air.
			if (timer.hasReach(Timer.TimeUnit.SECONDS, ((int) delay.getValue())) && mc.getPlayer().isOnGround()) {
				//Jump!
				mc.getPlayer().jump();
				//Reset the timer since we have moved now.
				timer.reset();
			}
		});
	}

	@Override
	protected void onEnable() {
		//Check if we're in game, since this mod could be enabled when the game starts
		if (hasWorld()) {
			//Set the last location to the player's current location
			lastLocation = mc.getPlayer().getLocation();
		}
	}
}
