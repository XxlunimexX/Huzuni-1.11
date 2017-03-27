package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.RenderManager;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.Toggleable;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.mcwrapper.api.MCWrapperHooks;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.halalaboos.mcwrapper.api.util.TextColor;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import org.lwjgl.input.Keyboard;

import java.util.Optional;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPolygonOffset;

// TODO: tag renderer for each entity. Modular approach for each tag for each entity type, as entities will have their own information with their own displays.
public class Nametags extends BasicMod implements RenderManager.Renderer {

	private final Toggleable armor = new Toggleable("Armor", "Render player armor above their heads");
	private final Toggleable enchants = new Toggleable("Enchants", "Render player enchantments over their items");
	private final Toggleable ping = new Toggleable("Ping", "Render player ping above their heads");
	private final Toggleable invisibles = new Toggleable("Invisible", "Render nameplates on invisible entities.");
	private final Toggleable scale = new Toggleable("Scale", "Scale the nameplates as you are further from the player");

	private final Value opacity = new Value("Opacity", "%", 0F, 30F, 100F, 1F, "Opacity of the name plate");
	private final Value scaleValue = new Value("Scale Amount", "%", 1F, 2F, 3F, "Amount the name plates will be scaled");

	private final Mode<String> healthMode = new Mode<>("Health", "Style the health will be rendered", "None", "Numerical", "Percentage");

	public Nametags() {
		super("Nametags", "Render custom nameplates over entities", Keyboard.KEY_P);
		this.setCategory(Category.VISUAL);
		addChildren(armor, enchants, ping, invisibles, scale, healthMode, scaleValue, opacity);
		this.settings.setDisplayable(false);
		armor.setEnabled(true);
		enchants.setEnabled(true);
		scale.setEnabled(true);
		healthMode.setSelectedItem(2);
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
		glLineWidth(huzuni.settings.lineSize.getValue());
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
		return getMinecraft().shouldShowGui() && !player.isNPC() && !(invisibles.isEnabled() && player.isInvisible())
				&& player != getMinecraft().getViewEntity() && !player.isDead();
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
		int color = getColor(player, huzuni.friendManager.isFriend(player.name()), player.isSneaking());
		//The interpolated distance, smoother scaling
		double dist = player.getInterpolatedPosition().distanceTo(getPlayer().getInterpolatedPosition());
		//The scale of the nameplate
		double scale = (dist / 8) / (1.5F + (2F - scaleValue.getValue()));
		//Prevents the nameplate from getting too small, and also locks the scale if scaling is disabled
		if (scale < 1D || !this.scale.isEnabled()) scale = 1;

		//The text to render on the nameplate.  Includes the name, health (if enabled), and ping (if enabled)
		String text = huzuni.friendManager.getAlias(player.name()) + getHealth(player) + getPing(player);
		//The width of the name, for centering and sizing the background rectangle.
		int width = getTextRenderer().getWidth(text);
		renderPlate(player, pos, scale, width, color, text);
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
		GLUtils.glColor(0F, 0F, 0F, (opacity.getValue()) / 100F);
		//Render the nameplate background
		GLUtils.drawBorderRect(-width / 2 - 2, -2, width / 2 + 2, 10, 2F);
		//Reset the color back to white
		GLUtils.glColor(1F, 1F, 1F, 1F);
		//Render the text, centered
		getTextRenderer().render(text, -width / 2, 0, color, true);
		//Render armor if it is enabled
		if (armor.isEnabled()) renderItems(player, -4);
		getGLStateManager().popMatrix();
	}

	/**
	 * Renders the specified Player's items, as well as enchants if the option for either are enabled.
	 *
	 * @param player The target Player
	 * @param rY The y position to start rendering (renderY)
	 */
	private void renderItems(Player player, float rY) {
		int totalItems = 0;
		glPolygonOffset(1.0F, -2000000.0F);
		getGLStateManager().enablePolygonOffset();
		getGLStateManager().enableDepth();
		getGLStateManager().depthMask(true);
		getGLStateManager().pushMatrix();
		for (int i = 0; i < 4; i++)
			totalItems++;
		totalItems++;
		int itemSize = 18, center = (-itemSize / 2), halfTotalSize = ((totalItems * itemSize) / 2 - itemSize) + (itemSize / 2), count = 0;
		draw3dItem(player.getHeldItem(Hand.MAIN), (center - halfTotalSize) + itemSize * count + 2, (int) rY - 16);
		if (enchants.isEnabled())
			renderEnchantments(player.getHeldItem(Hand.MAIN), (center - halfTotalSize) + itemSize * count + 2, (int) rY - 16, 0.5F);
		count++;
		for (int i = 4; i > 0; i--) {
			ItemStack armor = player.getPlayerInventory().getArmorStack(i - 1);
			draw3dItem(armor, (center - halfTotalSize) + itemSize * count, (int) rY - 16);
			if (enchants.isEnabled())
				renderEnchantments(armor, (center - halfTotalSize) + itemSize * count, (int) rY - 16, 0.5F);
			count++;
		}
		getGLStateManager().popMatrix();
		getGLStateManager().disablePolygonOffset();
		getGLStateManager().disableDepth();
		getGLStateManager().depthMask(false);
	}

	private void draw3dItem(ItemStack itemStack, int x, int y) {
		if (itemStack == null) return;
		itemStack.render3D(x, y);
	}

	/**
	 * Renders the name/level of all of the enchantments on the specified {@link ItemStack}
	 *
	 * @param item The item to obtain the list of enchants from
	 * @param x The x-position to render the text
	 * @param y The y-position to render the text
	 * @param scale The scale of the text
	 */
	private void renderEnchantments(ItemStack item, float x, float y, float scale) {
		float scaleInverse = 1F / scale, increment = 10F / scaleInverse;
		//Check if the item has any enchants
		if (item.getEnchants() != null) {
			//Loop through the enchants
			for (int i = 0; i < item.getEnchants().size(); i++) {
				//Get the name of the enchantment to render
				String name = item.getEnchants().get(i);

				//Setup rendering and draw the text
				getGLStateManager().pushMatrix();
				getGLStateManager().scale(scale, scale, scale);
				getTextRenderer().render(name, x * scaleInverse, ((int) y + (increment * i)) * scaleInverse, 0xFFFFFF);
				getGLStateManager().popMatrix();
			}
		}
	}

	/**
	 * Returns the specified Player's ping, used to see their connection to the server.  The ping is displayed in
	 * the format of "{@code # ms}" and colored based on how good or bad their connection is.
	 *
	 * @return The ping
	 */
	private String getPing(Player player) {
		//Return nothing if ping isn't enabled.
		if (!this.ping.isEnabled()) return "";
		Optional<PlayerInfo> playerInfo = getMinecraft().getNetworkHandler().getInfo(player.getUUID());
		//Check if the playerinfo for this player exists.
		if (playerInfo.isPresent()) {
			//Get the raw ping value
			int ping = playerInfo.get().getPing();
			//Color the ping based on how good/bad it is
			TextColor pingFormat = getFormatted(ping >= 100 && ping < 150, ping >= 150 && ping < 200, ping >= 200);
			//Return the formatted ping
			return " " + pingFormat + ping + "ms";
		}
		return "";
	}

	/**
	 * Used to get the health of the specified Player to display on the nameplate, based on the current
	 * {@link #healthMode health mode}.
	 *
	 * @return The Player's health.
	 */
	private String getHealth(Player player) {
		//Obtain the health percentage by dividing the current health by the max health.
		float healthPercentage = player.getHealthData().getCurrentHealth() / player.getHealthData().getMaxHealth();
		//The color to render the health with
		TextColor healthFormat = getFormatted(healthPercentage > 0.5 && healthPercentage < 0.75, healthPercentage > 0.25 && healthPercentage <= 0.5, healthPercentage <= 0.25);
		return healthMode.getSelected() == 1 ? " " + healthFormat + String.format("%.2f", player.getHealthData().getCurrentHealth()) : healthMode.getSelected() == 2 ? " " + healthFormat + (int) (healthPercentage * 100) + "%" : "";
	}

	/**
	 * When rendering information such as ping or health, the text will be colored using this method.  If none of the
	 * conditions are met, then it will return {@link TextColor#GREEN}.
	 *
	 * @param yellow If the color should be yellow
	 * @param orange If the color should be gold/orange
	 * @param red If the color should be red
	 *
	 * @return The color
	 */
	private TextColor getFormatted(boolean yellow, boolean orange, boolean red) {
		return yellow ? TextColor.YELLOW : orange ? TextColor.GOLD : red ? TextColor.RED : TextColor.GREEN;
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
