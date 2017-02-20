package net.halalaboos.mcwrapper.api.network;

public interface ServerInfo {

	/**
	 * @return The name of the server.
	 */
	String getName();

	/**
	 * @return The server's IP.
	 */
	String getIP();

	/**
	 * @return The players on the server.
	 */
	String getPopulation();

	/**
	 * @return The server's MOTD.
	 */
	String getMOTD();

	/**
	 * @return The server's ping.
	 */
	long getPing();

	/**
	 * @return Whether or not the server is a LAN server.
	 */
	boolean isLAN();

}
