package net.halalaboos.tukio;

import java.util.*;

/**
 * The EventManager handles the publishing and subscribing of {@link Event Events} and {@link Subscriber Subscribers}.
 */
public class EventManager {
	/**
	 * Contains all of the registered {@link Event Events} and a list of their {@link Subscriber Subscribers}.
	 */
	private Map<Class<? extends Event>, HashSet<Subscriber>> eventMap = new HashMap<>();

	/**
	 * Subscribes the specified {@link Subscriber} to the target {@link Event} class.
	 *
	 * @param event The Event's class.
	 * @param subscriber The Subscriber that will subscribe to the Event
	 * @param <T> The Event
	 */
	public <T extends Event> void subscribe(Class<T> event, Subscriber<T> subscriber) {
		eventMap.computeIfAbsent(event, k -> new HashSet<>()).add(subscriber);
	}

	/**
	 * Unsubscribes the specified {@link Subscriber} from the target {@link Event} class.
	 *
	 * @param event The Event's class.
	 * @param subscriber The Subscriber that will unsubscribe from the Event
	 * @param <T> The Event
	 */
	public <T extends Event> void unsubscribe(Class<T> event, Subscriber<T> subscriber) {
		eventMap.computeIfAbsent(event, k -> new HashSet<>()).remove(subscriber);
	}

	/**
	 * Publishes the specified {@link Event}.
	 *
	 * <p>When an {@link Event} is published, all of the registered {@link Subscriber Subscribers} to it will
	 * be called, running {@link Subscriber#read(Event)}.</p>
	 *
	 * @param event The {@link Event} to publish
	 * @param <T> The {@link Event} to publish
	 * @return The {@link Event}
	 */
	@SuppressWarnings("unchecked")
	public <T extends Event> T publish(T event) {
		//Get all of the subscribers that are subscribed to the event
		HashSet<Subscriber> subscribers = eventMap.get(event.getClass());

		//If the list of subscribers isn't empty...
		if (subscribers != null) {
			if (!subscribers.isEmpty()) {
				//Loop through each subscriber
				for (Subscriber subscriber : subscribers) {
					//And dispatch them!
					subscriber.read(event);
				}
			}
		}
		return event;
	}
}