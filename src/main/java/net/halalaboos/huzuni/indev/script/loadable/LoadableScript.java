package net.halalaboos.huzuni.indev.script.loadable;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.indev.script.Evaluation;
import net.halalaboos.huzuni.indev.script.ScriptManager;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.Reader;

/**
 * Base class for the loadable script files. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public abstract class LoadableScript {

    protected final Huzuni huzuni;

    protected final String tag;

    public LoadableScript(String tag, Huzuni huzuni, ScriptManager scriptManager, Reader reader) throws ScriptException {
        this.tag = tag;
        this.huzuni = huzuni;
        ScriptEngine engine = scriptManager.getScriptEngine();
        loadGlobals(engine);
        load(new Evaluation(engine, engine.eval(reader)));
    }

    /**
     * Invoked to load the global variables used by this script.
     * */
    public abstract void loadGlobals(ScriptEngine engine);

    /**
     * Loads any objects necessary from this script.
     * */
    public abstract void load(Evaluation evaluation);

    /**
     * Invoked to destroy all information regarding this script.
     * */
    public abstract void destroy();

}
