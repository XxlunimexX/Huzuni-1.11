package net.halalaboos.huzuni.indev.script;

import net.halalaboos.huzuni.Huzuni;
import org.apache.logging.log4j.Level;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.util.HashSet;
import java.util.Set;

/**
 * Safely invokes functions using an invocable. <br/>
 * Created by Brandon Williams on 2/18/2017.
 */
public class SafeInvoker {

    private final Invocable invocable;

    // Each function which cannot be found is stored within this set.
    private final Set<String> invalidfunctions = new HashSet<>();

    public SafeInvoker(Invocable invocable) {
        this.invocable = invocable;
    }

    /**
     * Safely invokes the function. Passes the arguments provided.
     * */
    public void safeInvoke(String functionName, Object... args) {
        try {
            if (!invalidfunctions.contains(functionName))
                invocable.invokeFunction(functionName, args);
        } catch (ScriptException e) {
            Huzuni.LOGGER.log(Level.WARN, e.getMessage());
        } catch (NoSuchMethodException e) {
            invalidfunctions.add(functionName);
        }
    }

}
