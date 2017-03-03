package net.halalaboos.tukio;

import java.util.*;

public class EventManager {

	private Map<Class<? extends Event>, List<Subscriber>> eventMap = new HashMap<>();

	public <T extends Event> void subscribe(Class<T> event, Subscriber<T> subscriber) {
		eventMap.computeIfAbsent(event, k -> new ArrayList<>()).add(subscriber);
	}

	public <T extends Event> void unsubscribe(Class<T> event, Subscriber<T> subscriber) {
		eventMap.computeIfAbsent(event, k -> new ArrayList<>()).remove(subscriber);
	}

	@SuppressWarnings("unchecked")
	public <T extends Event> T publish(T event) {
		Collection<Subscriber> subscribers = eventMap.get(event.getClass());
		if (!subscribers.isEmpty()) {
			for (Subscriber subscriber : subscribers) {
				subscriber.read(event);
			}
		}
		return event;
	}
}