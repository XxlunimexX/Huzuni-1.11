package net.halalaboos.huzuni.indev.script.meta;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.gui.Notification;
import net.halalaboos.mcwrapper.api.MCWrapper;

/**
 * Wrapper Huzuni class used by scripts. <br/>
 * Created by Brandon Williams on 2/18/2017.
 */
public final class ScriptHuzuni {

    private final Huzuni huzuni;

    public ScriptHuzuni(Huzuni huzuni) {
        this.huzuni = huzuni;
    }

    /**
     * @return The name of the mod.
     * */
    public String getName() {
        return Huzuni.NAME;
    }

    /**
     * @return The version of the mod.
     * */
    public String getVersion() {
        return Huzuni.VERSION;
    }

    /**
     * @return The build number of the mod.
     * */
    public int getBuild() {
        return Huzuni.BUILD_NUMBER;
    }

    /**
     * @return The minecraft version of the mod.
     * */
    public String getMcVersion() {
        return MCWrapper.getMinecraftVersion();
    }

    /**
     * Adds a message to the minecraft chat.
     * */
    public void addChatMessage(String message) {
        huzuni.addChatMessage(message);
    }

    /**
     * Adds an info notification.
     * */
    public void addInfoNotification(String source, int duration, String... message) {
        huzuni.addNotification(Notification.NotificationType.INFO, source, duration, message);
    }

    /**
     * Adds an error notification.
     * */
    public void addErrorNotification(String source, int duration, String... message) {
        huzuni.addNotification(Notification.NotificationType.ERROR, source, duration, message);
    }

    /**
     * Adds a confirmation notification.
     * */
    public void addConfirmNotification(String source, int duration, String... message) {
        huzuni.addNotification(Notification.NotificationType.CONFIRM, source, duration, message);
    }

    /**
     * Adds an inquiry notification.
     * */
    public void addInquireNotification(String source, int duration, String... message) {
        huzuni.addNotification(Notification.NotificationType.INQUIRE, source, duration, message);
    }
}
