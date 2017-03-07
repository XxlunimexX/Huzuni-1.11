package net.halalaboos.huzuni.indev.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.Reader;

/**
 * Handle used for scripts. Allows for invoking methods and accessing variables found within the scripts scope along with setting up the globals for this script before evaluating it. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ScriptHandle {

    private final String tag;

    private final ScriptEngine engine;

    private final SafeInvoker safeInvoker;

    private Evaluation evaluation;

    public ScriptHandle(String tag, ScriptManager scriptManager) {
        this.tag = tag;
        this.engine = scriptManager.getScriptEngine();
        this.safeInvoker = new SafeInvoker((Invocable) engine);
    }

    /**
     * Adds the given value to the engine scope with the variable name.
     * */
    public void addGlobal(String variableName, Object object) {
        engine.put(variableName, object);
    }

    /**
     * Loads any objects necessary from this script.
     * */
    public Evaluation evaluate(Reader reader) throws ScriptException {
        if (this.evaluation == null) {
            this.evaluation = new Evaluation(engine, engine.eval(reader));
            return evaluation;
        }
        return null;
    }

    /**
     * Safely invokes the function name with the given arguments.
     * */
    public void safeInvoke(String functionName, Object... args) {
        if (safeInvoker != null) {
            safeInvoker.safeInvoke(functionName, args);
        }
    }

    public String getTag() {
        return tag;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }
}
