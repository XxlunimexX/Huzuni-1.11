package net.halalaboos.mcwrapper.api.item.types;

public interface Bow extends Throwable {

	static float getVelocity(int charge) {
		float f = (float)charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		return f;
	}

}
