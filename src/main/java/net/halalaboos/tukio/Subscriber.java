package net.halalaboos.tukio;

/**
 * Represents an object that 'subscribes' to an {@link Event}.
 *
 * <p>Whenever an Event is {@link EventManager#publish(Event) published}, all of the registered Subscribers of
 * that event will call {@link #read(Event)}.</p>
 *
 * @param <T> The Event this Subscriber subscribes to.
 */
@FunctionalInterface
public interface Subscriber<T extends Event> {

	/**
	 * Performed every time the {@link T event} is {@link EventManager#publish(Event) published}.
	 *
	 * TODO: Different name?
	 *
	 * @param event The target event.
	 */
	void read(T event);

}
