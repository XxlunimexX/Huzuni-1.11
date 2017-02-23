package net.halalaboos.mcwrapper.api.entity.living.player;

public enum GameType {

	NONE(-1, "", ""),
	SURVIVAL(0, "survival", "s"), CREATIVE(1, "creative", "c"),
	ADVENTURE(2, "adventure", "a"), SPECTATOR(3, "spectator", "sp");

	private int id;

	private String name;
	private String shortName;

	GameType(int id, String name, String shortName) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}
}
