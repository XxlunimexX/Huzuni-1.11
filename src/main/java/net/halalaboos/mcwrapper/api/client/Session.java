package net.halalaboos.mcwrapper.api.client;

public interface Session {

	/**
	 * Sets the current Session.
	 * @param params A String array containing the username, player ID, and token
	 */
	void set(String... params);

	String name();

	String[] getParams();

}
