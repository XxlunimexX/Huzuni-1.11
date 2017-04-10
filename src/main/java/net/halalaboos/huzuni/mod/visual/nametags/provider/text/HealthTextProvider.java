package net.halalaboos.huzuni.mod.visual.nametags.provider.text;

import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.mod.visual.nametags.provider.TagTextProvider;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;

//todo doc
public class HealthTextProvider implements TagTextProvider {

	private Mode healthMode;

	public HealthTextProvider(Mode healthMode) {
		this.healthMode = healthMode;
	}

	/**
	 * Used to get the health of the specified Player to display on the nameplate, based on the current
	 * {@link #healthMode health mode}.
	 *
	 * @return The Player's health.
	 */
	@Override
	public String getText(Living entity) {
		return getColor(entity) + (healthMode.getSelected() == 1 ? getNumericalText(entity) : getPercentText(entity));
	}

	private String getNumericalText(Living entity) {
		return String.format("%.2f", entity.getHealthData().getCurrentHealth());
	}

	private String getPercentText(Living entity) {
		return (int) (entity.getHealthData().getHealthPercentage() * 100) + "%";
	}

	private TextColor getColor(Living entity) {
		//Obtain the health percentage by dividing the current health by the max health.
		float healthPercentage = entity.getHealthData().getHealthPercentage();
		//The color to render the health with
		return getFormatted(healthPercentage > 0.5 && healthPercentage < 0.75,
				healthPercentage > 0.25 && healthPercentage <= 0.5,
				healthPercentage <= 0.25);
	}

	@Override
	public boolean isEnabled(Living entity) {
		return healthMode.getSelected() > 0 && entity.getHealthData().getHealthPercentage() < 1;
	}
}
