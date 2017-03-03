package net.halalaboos.tukio;

public interface Subscriber<T extends Event> {
	void read(T event);
}
