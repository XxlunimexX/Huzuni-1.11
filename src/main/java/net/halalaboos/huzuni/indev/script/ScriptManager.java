package net.halalaboos.huzuni.indev.script;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.indev.script.meta.ScriptHuzuni;
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
 * Manages all evaluations running throughout the mod. <br/>
 * Created by Brandon Williams on 2/17/2017.
 */
public class ScriptManager {

    // Each script is mapped based on it's file name or given name.
    private final Map<String, Evaluation> evaluations = new HashMap<>();

    // We create a class filter to filter the banned classes and allowed packages.
    private final ClassFilter defaultClassFilter = new ScriptFilter(new String[] {
            "net.halalaboos.huzuni",
            "java.util",
            "java.lang"
    }, new String[] {});

    // This is used to ensure that the evaluations do not have access to certain functions.
    // Preval = pre-evaluation
    private final String defaultPreval = "quit = function() {};\n" +
            "exit = function() {};\n" +
            //"print = function() {};\n" +
            //"echo = function() {};\n" +
            "load = function() {};\n" +
            "loadWithNewGlobal = function() {};\n";

    private final NashornScriptEngineFactory scriptEngineFactory = new NashornScriptEngineFactory();

    private final Huzuni huzuni;

    private final ScriptHuzuni scriptHuzuni;

    public ScriptManager(Huzuni huzuni) {
        this.huzuni = huzuni;
        scriptHuzuni = new ScriptHuzuni(huzuni);
    }

    /**
     * Initialize this script manager.
     * */
    public void init() {

    }

    /**
     * Loads a script from the file given and indexes it with the file name.
     * */
    public void loadScriptPackage(File file) {
        try {
            loadScriptPackage(file.getName(), new FileReader(file));
        } catch (FileNotFoundException e) {
            Huzuni.LOGGER.log(Level.WARN, e.getMessage());
        }
    }

    /**
     * Loads a script package from a reader.
     * */
    public void loadScriptPackage(String name, Reader reader) {
        // TODO: create this
    }

    /**
     * @return The default script engine used by scripts.
     * */
    public ScriptEngine getScriptEngine() {
        return getScriptEngine(defaultClassFilter);
    }

    /**
     * @return A ScriptEngine using the class filter provided and evaluating a default pre-evaluation.
     * */
    public ScriptEngine getScriptEngine(ClassFilter classFilter) {
        return getScriptEngine(classFilter, defaultPreval);
    }

    /**
     * @return A ScriptEngine using the class filter provided and evaluation the string provided immediately.
     * */
    public ScriptEngine getScriptEngine(ClassFilter classFilter, String preval) {
        ScriptEngine scriptEngine = scriptEngineFactory.getScriptEngine(classFilter);
        try {
            scriptEngine.put("huzuni", scriptHuzuni);
            scriptEngine.eval(preval);
        } catch (ScriptException e) {
            Huzuni.LOGGER.log(Level.FATAL, e.getMessage());
        }
        return scriptEngine;
    }

    public ClassFilter getDefaultFilter() {
        return defaultClassFilter;
    }

}
