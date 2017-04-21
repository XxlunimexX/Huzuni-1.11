package net.halalaboos.huzuni;

import net.halalaboos.huzuni.api.account.AccountManager;
import net.halalaboos.huzuni.api.gui.MinecraftFontRenderer;
import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.api.mod.ModManager;
import net.halalaboos.huzuni.api.mod.command.CommandManager;
import net.halalaboos.huzuni.api.mod.keybind.KeybindManager;
import net.halalaboos.huzuni.api.plugin.PluginManager;
import net.halalaboos.huzuni.api.task.ClickTask;
import net.halalaboos.huzuni.api.task.HotbarManager;
import net.halalaboos.huzuni.api.task.LookTask;
import net.halalaboos.huzuni.api.task.TaskManager;
import net.halalaboos.huzuni.gui.GuiManager;
import net.halalaboos.huzuni.gui.Notification;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.huzuni.mod.Patcher;
import net.halalaboos.huzuni.mod.combat.*;
import net.halalaboos.huzuni.mod.commands.*;
import net.halalaboos.huzuni.mod.mining.*;
import net.halalaboos.huzuni.mod.misc.*;
import net.halalaboos.huzuni.mod.misc.chat.ChatAnnoy;
import net.halalaboos.huzuni.mod.misc.chat.ChatMutator;
import net.halalaboos.huzuni.mod.movement.*;
import net.halalaboos.huzuni.mod.visual.*;
import net.halalaboos.huzuni.mod.visual.nametags.Nametags;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 *
 * */
public enum Huzuni {
	INSTANCE;

	public static final int BUILD_NUMBER = 21;
	public static String NAME = "Huzuni";
	public static final String VERSION = NAME + " 5.1";
	public static final String ASSETS_LOCATION = "/assets/minecraft/huzuni/";

	public static final Logger LOGGER = LogManager.getLogger("Huzuni");

	private final Patcher patcher = new Patcher(this);

	public final PluginManager pluginManager = new PluginManager();

	public final ModManager modManager = new ModManager(this);

	public final CommandManager commandManager = new CommandManager(this);

	public final FriendManager friendManager = new FriendManager(this);

	public final KeybindManager keybindManager = new KeybindManager();

	public final RenderManager renderManager = new RenderManager(this);

	public final WaypointManager waypointManager = new WaypointManager(this);

	public final TaskManager<LookTask> lookManager = new TaskManager<>("Look Manager", "Manage which mods will prioritize when modifying the player rotation.");

	public final HotbarManager hotbarManager = new HotbarManager();

	public final TaskManager<ClickTask> clickManager = new TaskManager<>("Click Manager", "Manage which mods will prioritize when sending window clicks.");

	public final GuiManager guiManager = new GuiManager();

	public final HuzuniSettings settings = new HuzuniSettings(this);

	public final MinecraftFontRenderer guiFontRenderer = new MinecraftFontRenderer(), chatFontRenderer = new MinecraftFontRenderer();

	public final ResourceCreator resourceCreator = new ResourceCreator();

	public final AccountManager accountManager = new AccountManager();

//	public final ScriptManager scriptManager = new ScriptManager(this);

	private File saveFolder = null;

    Huzuni() {}

    /**
     * Invoked when the game starts. Loads basic information.
     * */
	public void start() {
		File folder = getMinecraft().getSaveDirectory();
		guiFontRenderer.setFont(new Font("Verdana", Font.PLAIN, 18), true);
		chatFontRenderer.setFont(new Font("Verdana", Font.PLAIN, 18), true);
		guiManager.init();
		modManager.init();
		friendManager.init();
		keybindManager.init();
		waypointManager.init();
		settings.init();
		patcher.init();
//		scriptManager.init();
		
		saveFolder = new File(folder, "huzuni");
		if (!saveFolder.exists())
			saveFolder.mkdirs();
		settings.setFile(new File(saveFolder, "settings.json"));
		modManager.setFile(new File(saveFolder, "mods.json"));
		friendManager.setFile(new File(saveFolder, "friends.json"));
		guiManager.widgetManager.setFile(new File(saveFolder, "widgets.json"));
		waypointManager.setFile(new File(saveFolder, "waypoints.json"));
		pluginManager.setPluginFolder(new File(saveFolder, "plugins"));
		accountManager.setFile(new File(saveFolder, "accounts.json"));
		loadDefaults();
		pluginManager.init();
		settings.load();
		accountManager.load();
		friendManager.load();
		waypointManager.load();
		modManager.load();
		guiManager.load();
		new HuzuniUpdater(this).start();

		Runtime.getRuntime().addShutdownHook(new Thread(this::end));
	}

	/**
     * Invoked when the game ends. Saves content.
     * */
	public void end() {
		modManager.save();
		friendManager.save();
		waypointManager.save();
		guiManager.save();
		settings.save();
		pluginManager.save();
		accountManager.save();
	}

	/**
     * Loads the default mods.
     * */
	private void loadDefaults() {
		modManager.addMod(new Waypoints());
		modManager.addMod(new Autopotion());
		modManager.addMod(new Autoarmor());
		modManager.addMod(new Killaura());
		modManager.addMod(new Bowaimbot());
		modManager.addMod(new Nuker());
		modManager.addMod(new Autofarm());
		modManager.addMod(new Smasher());
		modManager.addMod(new Replica());
		modManager.addMod(new ESP());
		modManager.addMod(new Nametags());
		modManager.addMod(new StorageESP());
		modManager.addMod(new Projectiles());
		modManager.addMod(Xray.INSTANCE);
		modManager.addMod(new Antiknockback());
		modManager.addMod(new Speed());
		modManager.addMod(new Scaffold());
		modManager.addMod(new Safewalk());
		modManager.addMod(new ChatMutator());
		modManager.addMod(new Criticals());
		modManager.addMod(new Brightness());
		modManager.addMod(new Dolphin());
		modManager.addMod(new Fastladder());
		modManager.addMod(Flight.INSTANCE);
		modManager.addMod(Freecam.INSTANCE);
		modManager.addMod(new Glide());
		modManager.addMod(new Nofall());
		modManager.addMod(new Sneak());
		modManager.addMod(new Spider());
		modManager.addMod(new Step());
		modManager.addMod(new Fastplace());
		modManager.addMod(new Timer());
		modManager.addMod(new Autoquit());
		modManager.addMod(new Autofish());
		modManager.addMod(new Autotool());
		modManager.addMod(new Respawn());
		modManager.addMod(new Breadcrumb());
		modManager.addMod(new Speedmine());
		modManager.addMod(new Cheststealer());
		modManager.addMod(new Retard());
		modManager.addMod(new NoSlowdown());
		modManager.addMod(new ChatAnnoy());
		modManager.addMod(new NoEffect());
		modManager.addMod(new AntiAFK());
//		modManager.addMod(new ChatTranslator());
		commandManager.addCommand(new Help());
		commandManager.addCommand(new Say());
		commandManager.addCommand(new GetCoords());
		commandManager.addCommand(new GetIP());
		commandManager.addCommand(new UsernameHistory());
		commandManager.addCommand(new AllOff());
		commandManager.addCommand(new GetBrand());
		commandManager.addCommand(new Toggle());
		commandManager.addCommand(new SetFont());
		commandManager.addCommand(new Add());
		commandManager.addCommand(new Remove());
		commandManager.addCommand(new Clear());
		commandManager.addCommand(new Commands());
		commandManager.addCommand(new Mods());
		commandManager.addCommand(new Lenny());
		commandManager.addCommand(new Bind());
		commandManager.addCommand(new Authors());
		commandManager.addCommand(new Enchant());
		commandManager.addCommand(new VClip());
		commandManager.addCommand(new Debug());
		keybindManager.addKeybind(settings.keyOpenMenu);
		keybindManager.addKeybind(settings.keyOpenTest);
	}

	/**
	 * Prints the message to the in-game chat.
	 * */
	public void addChatMessage(String message) {
		MCWrapper.getMinecraft().printMessage(TextColor.BLUE + "[H] " + TextColor.GRAY + message);
	}

	public void addFormattedMessage(String message, Object... args) {
		addChatMessage(String.format(message, args));
	}

	/**
     * Adds the given notification to the list of notifications within the gui manager.
     * */
	public void addNotification(Notification notification) {
		this.guiManager.addNotification(notification);
	}

	/**
     * Simplifies adding notifications to the gui manager.
     * */
	public void addNotification(NotificationType notificationType, String source, int duration, String... message) {
		this.guiManager.addNotification(new Notification(notificationType, source, duration, message));
	}

	/**
     * Simplifies adding notifications to the gui manager.
     * */
	public void addNotification(NotificationType notificationType, Mod mod, int duration, String... message) {
		this.addNotification(notificationType, mod.getName(), duration, message);
	}
	
	public File getSaveFolder() {
		return this.saveFolder;
	}
	
}
