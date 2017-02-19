package net.halalaboos.mcwrapper.api.network;

public interface ServerInfo {

	String getName();

	String getIP();

	String getPopulation();

	String getMOTD();

	long getPing();

	boolean isLAN();

}
