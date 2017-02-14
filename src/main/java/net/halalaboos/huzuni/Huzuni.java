package net.halalaboos.huzuni;

import net.halalaboos.huzuni.api.event.EventManager;
import net.halalaboos.huzuni.api.mod.CommandManager;
import net.halalaboos.huzuni.api.mod.KeybindManager;
import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.api.mod.ModManager;
import net.halalaboos.huzuni.api.plugin.PluginManager;
import net.halalaboos.huzuni.api.task.ClickTask;
import net.halalaboos.huzuni.api.task.HotbarManager;
import net.halalaboos.huzuni.api.task.LookTask;
import net.halalaboos.huzuni.api.task.TaskManager;
import net.halalaboos.huzuni.gui.GuiManager;
import net.halalaboos.huzuni.gui.Notification;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.huzuni.mc.HuzuniIngameGui;
import net.halalaboos.huzuni.meme.MemeManager;
import net.halalaboos.huzuni.mod.Patcher;
import net.halalaboos.huzuni.mod.combat.*;
import net.halalaboos.huzuni.mod.commands.*;
import net.halalaboos.huzuni.mod.mining.*;
import net.halalaboos.huzuni.mod.misc.*;
import net.halalaboos.huzuni.mod.misc.chat.ChatMutator;
import net.halalaboos.huzuni.mod.movement.*;
import net.halalaboos.huzuni.mod.visual.*;
import net.halalaboos.huzuni.render.font.MinecraftFontRenderer;
import net.halalaboos.mcwrapper.api.util.TextColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;

/**
 *
 * */
public enum Huzuni {
	INSTANCE;

	public static final int BUILD_NUMBER = 19;
	public static String NAME = "Huzuni";
	public static final String VERSION = NAME + " 5.0.2";
	public static final String MCVERSION = "1.11.2";

	public static final Logger LOGGER = LogManager.getLogger("Huzuni");
	
	private final Patcher patcher = new Patcher();
		
	public final PluginManager pluginManager = new PluginManager();
		
	public final ModManager modManager = new ModManager(this);
	
	public final CommandManager commandManager = new CommandManager(this);
	
	public final FriendManager friendManager = new FriendManager(this);
	
	public final KeybindManager keybindManager = new KeybindManager();
	
	public final RenderManager renderManager = new RenderManager(this);
	
	public final WaypointManager waypointManager = new WaypointManager(this);
	
	public final CapeManager capeManager = new CapeManager(this);
	
	public final MemeManager memeManager = new MemeManager(this);
	
	public final EventManager<Object> eventManager = new EventManager<>();

	public final TaskManager<LookTask> lookManager = new TaskManager<>("Look Manager", "Manage which mods will prioritize when modifying the player rotation.");
	
	public final HotbarManager hotbarManager = new HotbarManager();

	public final TaskManager<ClickTask> clickManager = new TaskManager<>("Click Manager", "Manage which mods will prioritize when sending window clicks.");

	public final GuiManager guiManager = new GuiManager();
	
	public final HuzuniSettings settings = new HuzuniSettings(this);

	public final MinecraftFontRenderer guiFontRenderer = new MinecraftFontRenderer(), chatFontRenderer = new MinecraftFontRenderer();

	private File saveFolder = null;

    Huzuni() {}

    /**
     * Invoked when the game starts. Loads basic information.
     * */
	public void start() {
		Minecraft mc = Minecraft.getMinecraft();
		File folder = mc.mcDataDir;
		mc.ingameGUI = new HuzuniIngameGui(mc);

		guiFontRenderer.setFont(new Font("Verdana", Font.PLAIN, 18), true);
		chatFontRenderer.setFont(new Font("Verdana", Font.PLAIN, 18), true);
		guiManager.init();
		modManager.init();
		friendManager.init();
		keybindManager.init();
		waypointManager.init();
		capeManager.init();
		memeManager.init();
		settings.init();
		patcher.init();
		
		saveFolder = new File(folder, "huzuni");
		if (!saveFolder.exists())
			saveFolder.mkdirs();
		settings.setFile(new File(saveFolder, "settings.json"));
		modManager.setFile(new File(saveFolder, "mods.json"));
		friendManager.setFile(new File(saveFolder, "friends.json"));
		guiManager.widgetManager.setFile(new File(saveFolder, "widgets.json"));
		waypointManager.setFile(new File(saveFolder, "waypoints.json"));
		capeManager.setFile(new File(saveFolder, "capes.json"));
		pluginManager.setPluginFolder(new File(saveFolder, "plugins"));
		loadDefaults();
		pluginManager.init();
		settings.load();
		friendManager.load();
		waypointManager.load();
		capeManager.load();
		modManager.load();
		guiManager.load();
		new HuzuniUpdater(this).start();
	}

	/**
     * Invoked when the game ends. Saves content.
     * */
	public void end() {
		modManager.save();
		friendManager.save();
		waypointManager.save();
		capeManager.save();
		guiManager.save();
		settings.save();
		pluginManager.save();
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
		modManager.addMod(Nametags.INSTANCE);
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
		modManager.addMod(new Autodisconnect());
		modManager.addMod(new Autofish());
		modManager.addMod(new Autotool());
		modManager.addMod(new Respawn());
		modManager.addMod(new Breadcrumb());
		modManager.addMod(new MiddleClickFriends());
		modManager.addMod(new Speedmine());
		modManager.addMod(new Cheststealer());
		modManager.addMod(new Retard());
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
		keybindManager.addKeybind(settings.keyOpenMenu);
	}

	/**
	 * Prints the message to the in-game chat.
	 * */
	public void addChatMessage(String message) {
        addChatMessage(new TextComponentString(TextColor.BLUE + "[H] " + TextColor.GRAY + message));
	}

	/**
     * Adds the text component to the in-game chat.
     * */
	public void addChatMessage(ITextComponent component) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(component);
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
