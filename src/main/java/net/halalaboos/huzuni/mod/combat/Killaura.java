package net.halalaboos.huzuni.mod.combat;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.Node;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.task.LookTask;
import net.halalaboos.huzuni.api.util.EntityTracker;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.api.util.gl.Texture;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.entity.living.player.GameType;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.event.input.MouseEvent;
import net.halalaboos.mcwrapper.api.event.player.PostMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import org.lwjgl.input.Keyboard;

import java.util.Optional;
import java.util.Random;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Automatically attacks entities after passing a series of tests.
 * */
public class Killaura extends BasicMod implements Renderer {
	
	private final Texture select = new Texture("select.png");
	
	public final Toggleable players = new Toggleable("Players", "Attack players");
	public final Toggleable mobs = new Toggleable("Mobs", "Attack mobs");
	public final Toggleable animals = new Toggleable("Animals", "Attack animals");
	public final Toggleable invisibles = new Toggleable("Invisible", "Attack invisibles");

	public final Toggleable silent = new Toggleable("Silent", "The aimbot will be silent");
	public final Toggleable interact = new Toggleable("Interact", "Interacts with entities rather than hitting them");
	public final Toggleable smartAttack = new Toggleable("Smart attack", "Attacks entities when the attack power exceeds or reaches their health");
	public final Toggleable selection = new Toggleable("Entity selection", "Select specific entities to attack");
	public final Toggleable checkAge = new Toggleable("Check age", "Check the age of the entity before attacking");

	public final Toggleable strengthRandomization = new Toggleable("Randomize Strength", "Randomize the rate when attacking based on strength");
	public final Toggleable speedRandomization = new Toggleable("Randomize Speed", "Randomize the rate when attacking based on speed");

	public final Value strength = new Value("Strength", "%", 0F, 100F, 100F, 1F, "Attack strength");
	public final Value accuracy = new Value("Accuracy", "%", 0F, 100F, 100F, 1F, "Accuracy of attacks (hit/miss)");
	public final Value speed = new Value("Speed", "", 1F, 8F, 15F, "Attack speed (in hits per second)");
	public final Value reach = new Value("Reach", " blocks", 3F, 3.8F, 6F, "Attack reach");
	public final Value fov = new Value("FOV", "", 10F, 60F, 180F, 1F, "FOV you will attack entities inside of");
	public final Value rotationRate = new Value("Rotation rate", "", 2F, 10F, 180F, 1F, "Maximum rate the rotation will be updated (the smaller, the smoother)");

	public final Mode<String> priority = new Mode<>("Attack Priority", "Determines which entity will be selected for attack", "Closest to crosshair", "Closest to player", "Triggerbot");

	private final LookTask lookTask = new LookTask(this);
	
	private final EntityTracker tracker = new EntityTracker();
	
	private final Timer timer = new Timer(), clickTimer = new Timer();
	
	private final Random random = new Random();
	
	private Living entity, selectedEntity, pickedEntity;
		
	private float randomizedSpeed, randomizedStrength;
	
	private String hitOrMiss = null;
	
	private int clicks = 0;
	
	public Killaura() {
		super("Kill aura", "Attack entities surrounding the player", Keyboard.KEY_R);
		setAuthor("Halalaboos");

		Node entities = new Node("Entities", "Entities to target");
		entities.addChildren(players, mobs, animals, invisibles);
		this.addChildren(entities, silent, interact, smartAttack, selection, checkAge, priority, accuracy, strength, strengthRandomization, speed, speedRandomization, reach, fov, rotationRate);

		silent.setEnabled(true);
		players.setEnabled(true);
		mobs.setEnabled(true);
		animals.setEnabled(true);
		smartAttack.setEnabled(true);
		checkAge.setEnabled(true);
		this.setCategory(Category.COMBAT);
		huzuni.lookManager.registerTaskHolder(this);
		calculateRandomization();

		subscribe(MouseEvent.class, this::onMouseClick);
		subscribe(PreMotionUpdateEvent.class, this::onPreUpdate);
		subscribe(PostMotionUpdateEvent.class, this::onPostUpdate);
	}

	@Override
	public void onEnable() {
		huzuni.renderManager.addWorldRenderer(this);
		calculateRandomization();
		if (selection.isEnabled()) {
			huzuni.addNotification(NotificationType.INFO, this, 5000, "Double right-click to select entities for specified attack!");
		}
	}
	
	@Override
	public void onDisable() {
		huzuni.renderManager.removeWorldRenderer(this);
		huzuni.lookManager.withdrawTask(lookTask);
		tracker.reset();
		tracker.setEntity(null);
		hitOrMiss = null;
		pickedEntity = null;
		selectedEntity = null;
		entity = null;
	}

	private void onPreUpdate(PreMotionUpdateEvent event) {
		if (!huzuni.lookManager.hasPriority(this))
			return;
		if (getPlayer().isGameType(GameType.SPECTATOR) || getPlayer().isDead()) {
			tracker.reset();
			tracker.setEntity(null);
			huzuni.lookManager.withdrawTask(lookTask);
			return;
		}
		if (priority.getSelected() == 0) {
			entity = MinecraftUtils.getClosestEntity(reach.getValue(), 2.5F, invisibles.isEnabled(), mobs.isEnabled(), animals.isEnabled(), players.isEnabled(), checkAge.isEnabled());
		} else if (priority.getSelected() == 1) {
			entity = MinecraftUtils.getClosestEntityToPlayer(getPlayer(), reach.getValue(), 2.5F, invisibles.isEnabled(), mobs.isEnabled(), animals.isEnabled(), players.isEnabled(), checkAge.isEnabled());
		} else if (priority.getSelected() == 2) {
			this.pickAndVerifyEntity();
			this.triggerBot();
			huzuni.lookManager.withdrawTask(lookTask);
			return;
		}
		this.pickAndVerifyEntity();
		if (entity != null && MinecraftUtils.isWithinFOV(entity, fov.getValue())) {
			tracker.setRotationRate(rotationRate.getValue());
			tracker.setEntity(entity);
			tracker.updateRotations();
			lookTask.setRotations(tracker.getYaw(), tracker.getPitch());
			lookTask.setReset(silent.isEnabled());
			huzuni.lookManager.requestTask(this, lookTask);
		} else {
			tracker.reset();
			tracker.setEntity(null);
			huzuni.lookManager.withdrawTask(lookTask);
		}
	}

	private void onPostUpdate(PostMotionUpdateEvent event) {
		if (lookTask.isRunning()) {
			if (tracker.hasReached()) {
				if (entity != null && MinecraftUtils.isWithinDistance(entity, reach.getValue())) {
					attackEntity();
				}
			}
		}
	}

	/**
     * Finds an entity within the FOV of 10 (10 degrees in both directions from the player's cursor) and assigns the entity to the selected entity if possible.
     * */
	private void pickAndVerifyEntity() {
		if (selection.isEnabled()) {
			if (hasSelectedEntity()) {
				if (!MinecraftUtils.isAliveNotUs(selectedEntity)) {
					onSelectedRemoved(selectedEntity);
					selectedEntity = null;
				} else if (MinecraftUtils.isWithinDistance(selectedEntity, reach.getValue()))
					entity = selectedEntity;
				else
					entity = null;
			} else
				pickedEntity = MinecraftUtils.getEntityWithinFOV(10, invisibles.isEnabled(), mobs.isEnabled(), animals.isEnabled(), players.isEnabled(), checkAge.isEnabled());
		}
	}

	/**
     * Attacks the facing or selected entity.
     * */
	private void triggerBot() {
		Optional<Entity> mousedEntity = mc.getMousedEntity();
		if (mousedEntity.isPresent()) {
			Entity entity = mousedEntity.get();
			if (entity instanceof Living && MinecraftUtils.checkType(entity, invisibles.isEnabled(), mobs.isEnabled(), animals.isEnabled(), players.isEnabled())
					&& MinecraftUtils.isWithinDistance((Living) entity, reach.getValue())
					&& MinecraftUtils.checkTeam((Living) entity) && !huzuni.friendManager.isFriend(entity.name())) {
				this.entity = (Living) entity;
				if (selection.isEnabled() && hasSelectedEntity()) {
					if (isSelectedEntity(this.entity))
						attackEntity();
					else
						this.entity = null;
				} else
					attackEntity();
			} else
				this.entity = null;
		} else
			this.entity = null;
	}
	
	@Override
	public String getDisplayNameForRender() {
		return settings.getDisplayName() + (hitOrMiss == null ? "" : " (" + hitOrMiss + ")");
	}

	/**
     * Creates a new randomization value for the strength and speed randomization factors.
     * */
	private void calculateRandomization() {
		randomizedSpeed = speed.getRandom(random);
        randomizedStrength = strength.getRandom(random);
	}
	
	private void attackEntity() {
		float cooldown = getPlayer().getAttackStrength();
		float accuracyPercent = accuracy.getValue() / 100F;
		if (((timer.hasReach((int) (1000F / (speedRandomization.isEnabled() ? randomizedSpeed : this.speed.getValue()))) && (cooldown >= (strengthRandomization.isEnabled() ? (randomizedStrength / 100F) : (strength.getValue() / 100F)))) || calculateSmartAttack())) {
			if (accuracyPercent >= random.nextFloat()) {
				if (interact.isEnabled())
					getController().interactWith(entity, Hand.MAIN);
				else
					getController().attack(entity);
				calculateRandomization();
				hitOrMiss = null;
			} else
				hitOrMiss = "Miss";
			getPlayer().swingItem(Hand.MAIN);
			timer.reset();
		}
	}

	/**
     * Determines if the entity currently selected can be killed using the damage the player can do.
     * */
	private boolean calculateSmartAttack() {
		if (this.smartAttack.isEnabled()) {
			if (getPlayer().getHeldItem(Hand.MAIN).isPresent()) {
				float playerDamage = MinecraftUtils.calculatePlayerDamage(entity, getPlayer().getHeldItem(Hand.MAIN).get());
				if (playerDamage >= entity.getHealthData().getCurrentHealth())
					return true;
			}
		}
		return false;
	}


	@Override
	public void render(float partialTicks) {
		if (selection.isEnabled()) {
			if (entity != null && isSelectedEntity(entity))
				renderEntitySelection(selectedEntity, 1F, 0F, 0F);
			else if (hasSelectedEntity())
				renderEntitySelection(selectedEntity, 0F, 1F, 0F);
			else if (pickedEntity != null)
				renderEntitySelection(pickedEntity, 1F, 1F, 0F);
		}
	}

	/**
     * Renders the entity selection texture.
     * */
	private void renderEntitySelection(Living entity, float r, float g, float b) {
		getGLStateManager().pushMatrix();
		Vector3d renderPos = entity.getRenderPosition();
		float renderX = (float)renderPos.getX();
		float renderY = (float)renderPos.getY();
		float renderZ = (float)renderPos.getZ();
		GLUtils.prepareBillboarding(renderX, renderY + (entity.getHeight() / 2F), renderZ, false);
		GLUtils.glColor(r, g, b, 1F);
		select.bindTexture();
		select.render(-16F, -16F, 32, 32);
		getGLStateManager().disableTexture2D();
		getGLStateManager().popMatrix();
	}

	private void onMouseClick(MouseEvent event) {
		if (selection.isEnabled() && event.getButton() == MouseButton.RIGHT) {
			if (clickTimer.hasReach(500)) {
				clicks = 1;
			}  else {
				clicks++;
				if (clicks >= 2) {
					if (selectedEntity != null) {
						onSelectedRemoved(selectedEntity);
						selectedEntity = null;
						entity = null;
					} else {
						if (pickedEntity != null) {
							selectedEntity = pickedEntity;
							onSelectedAdded(selectedEntity);
						}
					}
					clicks = 0;
				}
			}
			clickTimer.reset();
		}
	}

	/**
     * @return True if the player has a selected entity.
     * */
	private boolean hasSelectedEntity() {
		return selectedEntity != null;
	}

	/**
     * @return True if the entity is the selected entity.
     * */
	private boolean isSelectedEntity(Living entity) {
		return selectedEntity == entity;
	}

	/**
     * Invoked when the entity is no longer selected.
     * */
	private void onSelectedRemoved(Living entity) {
		if (entity != null)
			huzuni.addNotification(NotificationType.ERROR, this, 5000, entity.name() + " is no longer selected!");
	}

    /***
     * Invoked when the entity is selected.
     */
	private void onSelectedAdded(Living entity) {
		huzuni.addNotification(NotificationType.CONFIRM, this, 5000, entity.name() + " is now selected!");
	}

}
