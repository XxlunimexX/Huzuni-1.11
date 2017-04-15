package net.halalaboos.huzuni.mod.visual.nametags;

import net.halalaboos.huzuni.RenderManager;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.mod.visual.nametags.provider.TagRenderProvider;
import net.halalaboos.huzuni.mod.visual.nametags.provider.TagTextProvider;
import net.halalaboos.huzuni.mod.visual.nametags.provider.render.ArmorRenderProvider;
import net.halalaboos.huzuni.mod.visual.nametags.provider.text.HealthTextProvider;
import net.halalaboos.huzuni.mod.visual.nametags.provider.text.PingTextProvider;
import net.halalaboos.mcwrapper.api.MCWrapperHooks;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Renders custom nameplates over Players with more information than vanilla nameplates, such as:
 * <ul>
 *     <li>Equipped armor, held item (and enchants) - via {@link ArmorRenderProvider}</li>
 *     <li>Health - via {@link HealthTextProvider}</li>
 *     <li>Ping - via {@link PingTextProvider}</li>
 * </ul>
 *
 * The nameplates are also not affected by things such as lighting or being behind blocks, like the vanilla nameplates
 * are.
 *
 * @author b
 *
 * TODO: tag renderer for each entity.
 * TODO: Modular approach for each tag for each entity type, as entities will have their own information.
 */
public class Nametags extends BasicMod implements RenderManager.Renderer {

	/**
	 * Whether or not the entity's armor should be rendered on the nameplates.
	 */
	private final Toggleable armor = new Toggleable("Armor", "Render player armor above their heads");

	/**
	 * Whether or not the enchants of the armor displayed with {@link #armor} should also display enchants.
	 */
	private final Toggleable enchants = new Toggleable("Enchants", "Render player enchantments over their items");

	/**
	 * Whether or not the entity's ping to the current server should be rendered on the nameplates.
	 */
	private final Toggleable ping = new Toggleable("Ping", "Render player ping above their heads");

	/**
	 * Whether or not nameplates should be rendered for invisible entities.
	 */
	private final Toggleable invisibles = new Toggleable("Invisible", "Render nameplates on invisible entities.");

	/**
	 * Whether or not the nameplates should be scaled based on the distance.  Useful for seeing names from far away.
	 */
	private final Toggleable scale = new Toggleable("Scale", "Scale the nameplates as you are further from the player");

	/**
	 * Whether or not the text on nameplates should be rendered with a shadow.  While this looks nicer, this also means
	 * that the text will be rendered twice due to how Minecraft does their font shadowing, which can lead to some
	 * potential performance issues.
	 */
	private final Toggleable shadow = new Toggleable("Shadow", "Whether or not to draw the text with a shadow (slower)");

	/**
	 * Sets how transparent the nameplate's background should be.
	 */
	private final Value opacity = new Value("Opacity", "%", 0F, 30F, 100F, 1F, "Opacity of the name plate");

	/**
	 * Sets the scale amount of the nameplate.
	 */
	private final Value scaleValue = new Value("Scale Amount", "%", 1F, 2F, 3F, "Amount the name plates will be scaled");

	/**
	 * Sets the display mode for health.
	 * <ul>
	 *     <li><strong>None</strong>: No health information will be displayed.</li>
	 *     <li><strong>Numerical</strong>: The amount of hearts will be displayed (e.g. '10').</li>
	 *     <li><strong>Percentage</strong>: The health percentage will be displayed (e.g. '40%').</li>
	 * </ul>
	 */
	private final Mode<String> healthMode = new Mode<>("Health", "Style the health will be rendered", "None", "Numerical", "Percentage");

	/**
	 * Contains all registered text providers, regardless of if they are enabled or not.
	 *
	 * These are used to add additional text to the nameplate.
	 */
	private final TagTextProvider[] textProviders;

	/**
	 * Contains all registered render providers, regardless of if they are enabled or not.
	 *
	 * Currently, the only implementation of this is the {@link ArmorRenderProvider}
	 */
	private final TagRenderProvider[] renderProviders;

	public Nametags() {
		super("Nametags", "Render custom nameplates over entities", Keyboard.KEY_P);
		this.setAuthor("brudin");
		this.setCategory(Category.VISUAL);
		addChildren(armor, enchants, ping, invisibles, scale, shadow, healthMode, scaleValue, opacity);
		this.settings.setDisplayable(false);
		armor.setEnabled(true);
		enchants.setEnabled(true);
		scale.setEnabled(true);
		shadow.setEnabled(true);
		healthMode.setSelectedItem(2);

		HealthTextProvider healthProvider = new HealthTextProvider(healthMode);
		PingTextProvider pingProvider = new PingTextProvider(ping);
		ArmorRenderProvider armorProvider = new ArmorRenderProvider(armor, enchants);

		this.textProviders = new TagTextProvider[] { healthProvider, pingProvider };
		this.renderProviders = new TagRenderProvider[] { armorProvider };
	}

	@Override
	public void onEnable() {
		huzuni.renderManager.addWorldRenderer(this);
	}

	@Override
	public void onDisable() {
		huzuni.renderManager.removeWorldRenderer(this);
		MCWrapperHooks.renderNames = true;
	}

	@Override
	public void render(float partialTicks) {
		//Loop through all players
		for (Player player : getWorld().getPlayers()) {
			//Check if a nameplate should be rendered for them
			if (shouldRenderTag(player)) {
				//Get the position to render the nameplate
				Vector3d renderPosition = player.getRenderPosition();
				//Setup the nameplate and then render it
				setupAndRender(player, renderPosition);
			}
		}
		getGLStateManager().lineWidth(huzuni.settings.lineSize.getValue());
		getGLStateManager().disableTexture2D();
		getGLStateManager().disableAlpha();
		MCWrapperHooks.renderNames = false;
	}

	/**
	 * Determines whether or not a nameplate should be rendered for the specified Player.
	 *
	 * @param player The target Player
	 * @return Whether or not the nameplate should render
	 */
	private boolean shouldRenderTag(Player player) {
		return mc.shouldShowGui() && !player.isNPC() && !(invisibles.isEnabled() && player.isInvisible())
				&& player != mc.getViewEntity() && !player.isDead();
	}

	/**
	 * Sets up the nameplate and renders it.  The reason for splitting this into two methods is just to keep things a
	 * little more tidy.
	 *
	 * @param player The target Player to render the nameplate on.
	 * @param pos The render position of the Player.
	 */
	private void setupAndRender(Player player, Vector3d pos) {
		//The color of the name
		final int color = getColor(player, huzuni.friendManager.isFriend(player.name()), player.isSneaking());
		//The interpolated distance, smoother scaling
		final double dist = player.getInterpolatedPosition().distanceTo(getPlayer().getInterpolatedPosition());
		//The scale of the nameplate
		double scale = (dist / 8) / (1.5F + (2F - scaleValue.getValue()));
		//Prevents the nameplate from getting too small, and also locks the scale if scaling is disabled
		if (scale < 1D || !this.scale.isEnabled()) scale = 1;

		//The text to render on the nameplate.  Includes the name, health (if enabled), and ping (if enabled)
		final String text = getPlateText(player);
		//The width of the name, for centering and sizing the background rectangle.
		final int width = getTextRenderer().getWidth(text);
		renderPlate(player, pos, scale, width, color, text);
	}

	/**
	 * Will return the {@link Player#name() Player's name}, as well as display the text provided by the enabled
	 * {@link #textProviders}.  This is used for the text on the nameplate.
	 *
	 * @param player The target Player (this will eventually be for all Living entities)
	 * @return The nameplate text.
	 */
	private String getPlateText(Player player) {
		final StringBuilder output = new StringBuilder();
		//Start with the player's name.
		output.append(huzuni.friendManager.getAlias(player.name()));
		//Iterate through the enabled text providers
		for (TagTextProvider textProvider : textProviders) {
			if (textProvider.isEnabled(player)) {
				//If it is enabled, add the text to the plate.
				output.append(" ").append(textProvider.getText(player));
			}
		}
		//Return our output.
		return output.toString();
	}

	/**
	 * Renders the nameplate for the specified Player.
	 *
	 * @param player The target Player to render the nameplate on.
	 * @param pos The render position of the Player.
	 * @param scale The scale of the nameplate
	 * @param width The width of the nameplate
	 * @param color The color of the nameplate text
	 * @param text The nameplate text
	 */
	private void renderPlate(Player player, Vector3d pos, double scale, int width, int color, String text) {
		getGLStateManager().pushMatrix();
		//Make it so the nameplate always faces us
		GLUtils.prepareBillboarding((float)pos.getX(), (float)pos.getY() + player.getHeight() + 0.5F, (float)pos.getZ(), true);
		getGLStateManager().scale(scale, scale, scale); //Scale the nameplate
		if (this.scale.isEnabled()) getGLStateManager().translate(0, -scale, 0); //Move the nameplate based on the size

		//Color the background of the nameplate
		if (opacity.getValue() > 0) {
			GLUtils.glColor(0F, 0F, 0F, (opacity.getValue()) / 100F);
			//Render the nameplate background
			GLUtils.drawBorderRect(-width / 2 - 2, -2, width / 2 + 2, 10, 2F);
		} else {
			getGLStateManager().enableTexture2D();
		}
		//Reset the color back to white
		GLUtils.glColor(1F, 1F, 1F, 1F);
		//Render the text, centered
		getTextRenderer().render(text, -width / 2, 0, color, shadow.isEnabled());
		for (TagRenderProvider renderProvider : this.renderProviders) {
			if (renderProvider.isEnabled(player)) {
				renderProvider.render(player, 0, -4 /* TODO */);
			}
		}
		getGLStateManager().popMatrix();
	}

	/**
	 * Provides the color for the text on the nameplate.  In the following scenarios,
	 * <ul>
	 *     <li>Sneaking - The color will be red.</li>
	 *     <li>Friend - The color will be based on {@link net.halalaboos.huzuni.FriendManager#color}</li>
	 *     <li>Teammate - The color will be based on the Player's team color. (see {@link net.halalaboos.huzuni.settings.Team#getTeamColor(Entity)}</li>
	 * </ul>
	 * If none of these conditions are met, the color will be white.
	 *
	 * @param player The target Player
	 * @param friend Whether or not the Player is a friend.
	 * @param sneaking Whether or not the Player is sneaking.
	 * @return The nameplate text color.
	 */
	private int getColor(Player player, boolean friend, boolean sneaking) {
		//Return the friend color if they're a friend
		if (friend) return huzuni.friendManager.getColor().getRGB();

		//Checks the player's team color
		if (huzuni.settings.team.isEnabled()) {
			if (huzuni.settings.team.isTeam(player)) return huzuni.settings.team.getColor();
			int teamColor = huzuni.settings.team.getTeamColor(player);
			if (teamColor != -1)
				return teamColor;
		}

		//If they're sneaking, return red, otherwise return white
		return sneaking ? 0xFF0000 : 0xFFFFFF;
	}
}
