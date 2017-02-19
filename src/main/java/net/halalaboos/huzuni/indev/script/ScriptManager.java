package net.halalaboos.huzuni.indev.script;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.RenderManager;
import org.apache.logging.log4j.Level;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages all scripts running throughout the mod. <br/>
 * Created by Brandon Williams on 2/17/2017.
 */
public class ScriptManager implements RenderManager.Renderer {

    private final Huzuni huzuni;

    private final ScriptHuzuni scriptHuzuni;

    private final ScriptEngine scriptEngine;

    // Each script is mapped based on it's file name or given name.
    private final Map<String, Script> scripts = new HashMap<>();

    // The scripts are somewhat 'sandboxed', meaning they're allowed to access only certain packages and files.
    private final String[] allowedPackages = new String[] {
            "net.halalaboos.huzuni",
            "java.util",
            "java.lang"
    };

    // The scripts are also banned from accessing certain classes as well.
    private final String[] bannedClasses = new String[] {

    };

    public ScriptManager(Huzuni huzuni) {
        this.huzuni = huzuni;
        scriptHuzuni = new ScriptHuzuni(huzuni);

        NashornScriptEngineFactory scriptEngineFactory = new NashornScriptEngineFactory();
        // We create a class filter to filter the banned classes and allowed packages.
        scriptEngine = scriptEngineFactory.getScriptEngine(new ClassFilter() {
            @Override
            public boolean exposeToScripts(String className) {
                for (String bannedClass : bannedClasses) {
                    if (className.equals(bannedClass))
                        return false;
                }
                for (String allowedPackage : allowedPackages) {
                    if (className.startsWith(allowedPackage))
                        return true;
                }
                return false;
            }
        });
        try {
            // This is done to ensure that the scripts do not have access to certain functions.
            scriptEngine.eval("quit = function() {};\n" +
                    "exit = function() {};\n" +
                    //"print = function() {};\n" +
                    //"echo = function() {};\n" +
                    "load = function() {};\n" +
                    "loadWithNewGlobal = function() {};\n");

            // Add global variables.
            scriptEngine.put("huzuni", scriptHuzuni);
            scriptEngine.put("gl", new ScriptGl(huzuni.fontManager));
        } catch (ScriptException e) {
            Huzuni.LOGGER.log(Level.FATAL, e.getMessage());
        }
    }

    /**
     * Initialize this script manager.
     * */
    public void init() {
        this.huzuni.renderManager.addOverlayRenderer(this);
    }

    /**
     * Loads a script from the file given and indexes it with the file name.
     * */
    public void loadScript(File file) {
        try {
            loadScript(file.getName(), new FileReader(file));
        } catch (FileNotFoundException e) {
            Huzuni.LOGGER.log(Level.WARN, e.getMessage());
        }
    }

    /**
     * Loads a script from a reader and indexes based on the name given.
     * */
    public void loadScript(String name, Reader reader) {
        try {
            // We store each scripts evaluation into a script object.
            Object evaluation = scriptEngine.eval(reader);
            scripts.put(name, new Script(evaluation));
        } catch (ScriptException e) {
            Huzuni.LOGGER.log(Level.WARN, e.getMessage());
        }
    }

    @Override
    public void render(float partialTicks) {
        for (Runnable runnable : scriptHuzuni.getRenderers().values()) {
            runnable.run();
        }
    }

    /**
     * Holds information regarding a scripts evaluation.
     * */
    private class Script {

        private Object evaluation;

        public Script(Object evaluation) {
            this.evaluation = evaluation;
        }
    }

}
