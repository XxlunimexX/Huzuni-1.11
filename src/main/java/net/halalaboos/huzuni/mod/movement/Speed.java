package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.Nameable;
import net.halalaboos.huzuni.api.node.Toggleable;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.event.player.MoveEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getInput;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Allows the player to move at a faster rate.
 * */
public class Speed extends BasicMod {
		
	public final Mode<SpeedMode> mode = new Mode<>("Mode", "Speed mode", new NoneMode(), new SprintMode());

    private final Toggleable bunnyHop = new Toggleable("Bunny hop", "Hops like a bunny");
    private final Toggleable stairs = new Toggleable("Stairs", "Automagically jumps up stairs");

    private final Value groundSpeed = new Value("Ground speed", 1F, 1F, 10F, "Movement speed on the ground");
    private final Value airSpeed = new Value("Air speed", 1F, 1F, 10F, "Movement speed in air");
	
	public Speed() {
		super("Speed", "Adjust player movement speed", Keyboard.KEY_M);
		setAuthor("Halalaboos");
		setCategory(Category.MOVEMENT);
		addChildren(bunnyHop, stairs, mode, groundSpeed, airSpeed);
		mode.setSelectedItem(1);
		subscribe(PreMotionUpdateEvent.class, event -> {
			boolean modifyMovement = shouldModifyMovement();
			mode.getSelectedItem().onUpdate(this, event);
			if (modifyMovement && getPlayer().isOnGround()) {
				if ((stairs.isEnabled() && getPlayer().isUnderStairs()) || bunnyHop.isEnabled()) {
					getPlayer().jump();
				}
			}
		});
		subscribe(MoveEvent.class, event -> {
			boolean onGround = getPlayer().isOnGround();
			event.setMotionX(event.getMotionX() * (onGround ? groundSpeed.getValue() : airSpeed.getValue()));
			event.setMotionZ(event.getMotionZ() * (onGround ? groundSpeed.getValue() : airSpeed.getValue()));
		});
	}
	
	@Override
	public void onDisable() {
		if (getPlayer() != null) getPlayer().setSprinting(false);
	}
	
	@Override
	public String getDisplayNameForRender() {
		return settings.getDisplayName() + String.format(" (%s)", mode.getSelectedItem());
	}

	/**
     * @return True if the player's given circumstances are ideal for modifying movement.
     * */
	boolean shouldModifyMovement() {
        return getInput().getForward() > 0 && !getPlayer().isSneaking()  &&
				!getPlayer().isCollided(Entity.CollisionType.HORIZONTAL) && getPlayer().getFood() > 6;
    }

    /**
     * The sprint mode forces the player to sprint when ideal.
     * */
    public static class SprintMode extends SpeedMode {

        SprintMode() {
            super("Sprint", "Forces the player to sprint.");
        }

        @Override
        public void onUpdate(Speed speed, PreMotionUpdateEvent event) {
			getPlayer().setSprinting(speed.shouldModifyMovement());
        }
    }

    /**
     * The default speed mode used, will apply no effects.
     * */
    public static class NoneMode extends SpeedMode {

        NoneMode() {
            super("None", "An empty speed mode, using only the movement speed modifiers.");
        }

        @Override
        public void onUpdate(Speed speed, PreMotionUpdateEvent event) {

        }
    }

    /**
     * Used within the mode of the speed mod. Allows for custom speed types.
     * */
	public static abstract class SpeedMode implements Nameable {

        private final String name, description;

        SpeedMode(String name, String description) {
            this.name = name;
            this.description = description;
        }

        /**
         * Invoked before and after sending motion updates.
         * */
        public abstract void onUpdate(Speed speed, PreMotionUpdateEvent event);

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
