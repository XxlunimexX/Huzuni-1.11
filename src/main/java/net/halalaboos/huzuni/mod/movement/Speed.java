package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.event.player.MoveEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getInput;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Used to allow the player to modify their movement speed, as well as automatically sprint, bunnyhop, etc.
 *
 * This mod has a couple different modes, which the player can cycle through.  If they want to just have faster movement,
 * the can use the {@link SpeedMode#NONE} mode, which will just change their movement speed and not sprint.  Otherwise,
 * they can use the {@link SpeedMode#SPRINT} mode.
 *
 * @author Halalaboos
 */
public class Speed extends BasicMod {

	/**
	 * The different modes the player can chose from.  This will also be displayed in-game, like "Speed (None)"
	 *
	 * @see SpeedMode
	 */
	private final Mode<SpeedMode> mode = new Mode<>("Mode", "Speed mode", SpeedMode.NONE, SpeedMode.SPRINT);

	/**
	 * Will automatically jump as the player moves.  This isn't really anything special like some other clients, just
	 * basic jumping.
	 */
    private final Toggleable bunnyHop = new Toggleable("Bunny hop", "Hops like a bunny");

	/**
	 * Will automatically jump as the player climbs stairs, resulting in faster movement upwards.
	 */
	private final Toggleable stairs = new Toggleable("Stairs", "Automagically jumps up stairs");

	/**
	 * The speed multiplier for on-ground movement.  In most anti-cheats, it's best to just leave this at 1.
	 */
    private final Value groundSpeed = new Value("Ground speed", 1F, 1F, 10F, "Movement speed on the ground");

	/**
	 * The speed multiplier for when the player is in the air.
	 */
	private final Value airSpeed = new Value("Air speed", 1F, 1F, 10F, "Movement speed in air");
	
	public Speed() {
		super("Speed", "Adjust player movement speed", Keyboard.KEY_M);
		setAuthor("Halalaboos");
		setCategory(Category.MOVEMENT);
		addChildren(bunnyHop, stairs, mode, groundSpeed, airSpeed);
		//Set the default mode to the Sprint mode
		mode.setSelectedItem(1);
		//Used to dispatch the modes as well as jump for the stairs/bunny-hop modes.
		subscribe(PreMotionUpdateEvent.class, event -> {
			//Dispatch the update event for the current selected mode.
			mode.getSelectedItem().onUpdate(this, event);
			//If the player is moving and on ground...
			if (shouldModifyMovement() && getPlayer().isOnGround()) {
				//If bunnyhop or stairs (and under stairs) is enabled, jump
				if ((stairs.isEnabled() && getPlayer().isUnderStairs()) || bunnyHop.isEnabled()) {
					getPlayer().jump();
				}
			}
		});

		//Used to adjust the ground/air speed
		subscribe(MoveEvent.class, event -> {
			//Check if the player is on-ground, if so, we will use the ground speed.  Otherwise, use air speed.
			float multiplier = getPlayer().isOnGround() ? groundSpeed.getValue() : airSpeed.getValue();
			event.setMotionX(event.getMotionX() * multiplier);
			event.setMotionZ(event.getMotionZ() * multiplier);
		});
	}
	
	@Override
	public void onDisable() {
		if (hasWorld()) {
			/*
			Disable sprinting when the mod is disabled.

			The player will stop sprinting regardless, but this can help if the user wants to stop sprinting right
			as the mod is disabled.
			 */
			getPlayer().setSprinting(false);
		}
	}

	/**
	 * Modifies the render name to display the selected mode.
	 */
	@Override
	public String getDisplayNameForRender() {
		return settings.getDisplayName() + String.format(" (%s)", mode.getSelectedItem());
	}

	/**
	 * Used to check whether or not the player can sprint in a vanilla context.
	 *
	 * For example, without this, we would just have the player always be sprinting.  That means they would be sprinting
	 * when they weren't moving, or collided against a wall, sneaking, etc.
	 *
	 * This helps the player look more 'legit' to other players.
	 */
	private boolean shouldModifyMovement() {
        return getInput().getForward() > 0 && !getPlayer().isSneaking()  &&
				!getPlayer().isCollided(Entity.CollisionType.HORIZONTAL) && getPlayer().getFood() > 6;
    }

	/**
	 * We have a couple different modes the user can choose from that can modify how the mod works.
	 *
	 * In most cases, the user will most likely just want to be sprinting, which is why {@link #SPRINT} is the default
	 * selected mode.
	 *
	 * In the case where the user wants to move quickly without having to sprint, they can use the {@link #NONE} mode,
	 * which will only adjust the movement speed.
	 */
	public enum SpeedMode implements Nameable {

		/**
		 * Only adjusts the moving speed, no need for any update event.
		 */
		NONE("None", "An empty speed mode, using only the movement speed modifiers"),

		/**
		 * Automatically sprints for the player given {@link #shouldModifyMovement()}
		 */
		SPRINT("Sprint", "Forces the player to sprint.") {
			@Override
			public void onUpdate(Speed speed, PreMotionUpdateEvent event) {
				getPlayer().setSprinting(speed.shouldModifyMovement());
			}
		};

		/**
		 * The name used for the {@link Mode} that this will be linked to.
		 */
        private final String name, description;

        SpeedMode(String name, String description) {
            this.name = name;
            this.description = description;
        }

		/**
		 * Invoked before sending motion updates to the server, serves as a simple 'tick' listener.
		 */
		public void onUpdate(Speed speed, PreMotionUpdateEvent event) {}

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
