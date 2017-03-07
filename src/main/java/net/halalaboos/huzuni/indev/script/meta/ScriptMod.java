package net.halalaboos.huzuni.indev.script.meta;

import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.indev.script.ScriptHandle;
import net.halalaboos.tukio.Subscriber;

import javax.script.ScriptException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Carries a script handle for a mod. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ScriptMod extends Mod {

    private final ScriptHandle script;

    public ScriptMod(String name, String description, ScriptHandle script) throws ScriptException {
        super(name, description);
        this.script = script;
        // script.addGlobal("subscribe", (Consumer<Object>) this::subscribe);
    }

    @Override
    protected void onToggle() {
        script.safeInvoke("onToggle");
    }

    @Override
    protected void onEnable() {
        script.safeInvoke("onEnable");
    }

    @Override
    protected void onDisable() {
        script.safeInvoke("onDisable");
    }

}
