package net.halalaboos.mcwrapper.api.client;

/**
 * Used to toggle individual client-side effects.  This was originally done with a bunch of getters in our {@link net.halalaboos.mcwrapper.api.MinecraftClient}
 * class, but this approach is a bit cleaner and easier to maintain.
 *
 * All effects in this class are enabled by default, due to them representing vanilla client-side effects.  Keep in mind
 * that this class only serves the purpose to control whether or not the effects can render.  This means that you can't
 * use this class to display the individual effects.
 */
public enum ClientEffects {

	/**
	 * The camera shake effect when the player takes damage.
	 */
	HURTCAM,
	/**
	 * Weather effects, such as the rain and snow overlays/particles.
	 */
	WEATHER,
	/**
	 * The various overlays such as fire or the pumpkin blur.
	 */
	OVERLAY,
	/**
	 * The blindness potion effect.
	 */
	BLINDNESS;

	/**
	 * By default, this is true, since given a vanilla context, these effects are going to be running by default.
	 *
	 * @see #isEnabled()
	 */
	private boolean enabled = true;

	/**
	 * We will be using a setter for the {@link #enabled} field since it looks a bit nicer, that's all.
	 *
	 * @see #isEnabled()
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Whether or not the effect is enabled.  This does not represent if the effect is active, just whether or not
	 * the effect <i>should</i> display, given the circumstances required.
	 *
	 * @return If the effect should render
	 */
	public boolean isEnabled() {
		return enabled;
	}
}
