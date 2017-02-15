package net.halalaboos.mcwrapper.api.entity.living.player;

public enum GameType {

	SURVIVAL(0), CREATIVE(1), ADVENTURE(2), SPECTATOR(3);

	private int id;

	GameType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
