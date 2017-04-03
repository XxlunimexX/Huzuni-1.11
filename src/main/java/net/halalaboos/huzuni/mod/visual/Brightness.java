package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.player.PostMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getAdapter;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Applies the 'Night Vision' {@link PotionEffect} to the player to make the world brighter.
 *
 * @author b
 *
 * TODO: Different bright modes, such as gamma, lightmap, etc.
 */
public class Brightness extends BasicMod {
		
	private Potion nightVision;
	
	public Brightness() {
		super("Brightness", "Light up your world as you recieve the night vision potion effect", Keyboard.KEY_C);
		setAuthor("brudin");
		this.setCategory(Category.VISUAL);
		//This is done every tick in case a situation happens where the Player's potion effects are cleared.
		subscribe(PostMotionUpdateEvent.class, event -> {
			int duration = 1000000;
			//Get the night vision potion instance from the potion registry
			if (nightVision == null) {
				nightVision = getAdapter().getPotionRegistry().getPotion("night_vision");
			}
			//Create an effect based on the potion
			PotionEffect effect = PotionEffect.from(nightVision, duration, 1, true, false);
			//Apply the effect
			getPlayer().addEffect(effect);
		});
	}

	@Override
	public void onDisable() {
		if (nightVision != null) {
			getPlayer().removeEffect(nightVision);
		}
	}

}
