package net.halalaboos.huzuni.api.mod;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.node.Node;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.MinecraftClient;
import net.halalaboos.tukio.Event;
import net.halalaboos.tukio.Subscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Node which is used for most features within the client. <br/>
 * Mods are toggleable nodes that can be categorized and have their own settings. <br/>
 * Each mod has an id that is assigned upon it's instantiation, meaning it is not always consistent.
 * */
public class Mod extends Node {

	protected MinecraftClient mc = MCWrapper.getMinecraft();

	protected final Huzuni huzuni = Huzuni.INSTANCE;

	protected boolean enabled = false;

	protected Category category = Category.NONE;

	private String author;

	public final ModSettings settings = new ModSettings(this);

	/**
	 * The Mod's Events to be dispatched when toggled.
	 */
	private Map<Class<? extends Event>, List<Subscriber>> eventMap = new HashMap<>();

	public Mod(String name, String description) {
		super(name, description);
		this.addChildren(settings);
	}

	/**
	 * Invoked when the mod is toggled.
	 * */
	protected void onToggle() {}

	/**
	 * Invoked when the mod is set enabled.
	 * */
	protected void onEnable() {}

	/**
	 * Invoked when the mod is set disabled.
	 * */
	protected void onDisable() {}

	/**
	 * Toggles the mod.
	 * */
	public void toggle() {
		this.setEnabled(!enabled);
		onToggle();
	}

	public Category getCategory() {
		return category;
	}

	protected void setCategory(Category category) {
		this.category = category;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if (this.enabled != enabled) {
			this.enabled = enabled;
			if (enabled)
				onEnable();
			else
				onDisable();

			updateEvents(enabled);
		}
	}

	/**
	 * Takes the {@link Event Events} and {@link Subscriber Subscribers} from the {@link #eventMap} and
	 * registers/unregisters them from the {@link net.halalaboos.tukio.EventManager}.
	 */
	private void updateEvents(boolean enabled) {
		if (eventMap.isEmpty()) return;
		for (Class<? extends Event> eventClass : this.eventMap.keySet()) {
			List<Subscriber> subscribers = eventMap.get(eventClass);
			for (Subscriber subscriber : subscribers) {
				if (enabled) {
					getEventManager().subscribe(eventClass, subscriber);
				} else {
					getEventManager().unsubscribe(eventClass, subscriber);
				}
			}
		}
	}

	/**
	 * Registers the specified {@code event} and {@code subscriber} to the {@link #eventMap}.
	 *
	 * <p>This means that to register/unregister events, all you have to do is use this method in your constructor.
	 * The Events will subscribe/unsubscribe based on whether or not the Mod is enabled.</p>
	 *
	 * <p>The reason it works this way is because the {@link net.halalaboos.tukio.EventManager} registers and
	 * unregisters Events in a way that requires the Event class and Subscriber to be specified.  This means
	 * that in the case of:</p>
	 *
	 * <p>
	 *     <code>
	 *         void onEnable() {
	 *             getEventManager().subscribe(TestEvent.class, event -> {});
	 *         }
	 *         void onDisable() {
	 *             getEventManager().unsubscribe(TestEvent.class, ???);
	 *         }
	 *     </code>
	 * </p>
	 *
	 * <p>Unsubscribing the Event wouldn't really be easy to do, unless you had a local {@link Subscriber} that you
	 * used as a parameter instead.</p>
	 *
	 * <p>Handling Event registration this way also requires less code to be written, as you will only need to call
	 * this method once, and it will subscribe/unsubscribe the Event(s) automatically when the mod is toggled.</p>
	 * @param event The Event class
	 * @param subscriber The Subscriber (or listener)
	 * @param <T> The Event
	 */
	protected <T extends Event> void subscribe(Class<T> event, Subscriber<T> subscriber) {
		eventMap.computeIfAbsent(event, k -> new ArrayList<>()).add(subscriber);
	}

	protected boolean hasWorld() {
		return getPlayer() != null && getWorld() != null;
	}

	/**
	 * @return This mod's display name for rendering within the enabled mods list.
	 * */
	public String getDisplayNameForRender() {
		return settings.getDisplayName();
	}

	@Override
	public boolean hasNode(JsonObject json) {
		JsonElement name = json.get("name");
		return name != null && name.getAsString().equals(getName());
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			setEnabled(json.get("enabled").getAsBoolean());
		}
	}

	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		json.addProperty("name", getName());
		json.addProperty("enabled", isEnabled());
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}
}
