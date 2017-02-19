package net.halalaboos.huzuni.indev.script;

import net.halalaboos.huzuni.indev.script.loadable.LoadableScript;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brandon Williams on 2/19/2017.
 */
public class ScriptPackage {

    private final Map<String, LoadableScript> loadedScripts = new HashMap<>();

    /**
     * Destroys all loaded scripts.
     * */
    public void destroy() {
        for (LoadableScript script : loadedScripts.values()) {
            script.destroy();
        }
        loadedScripts.clear();
    }
}
