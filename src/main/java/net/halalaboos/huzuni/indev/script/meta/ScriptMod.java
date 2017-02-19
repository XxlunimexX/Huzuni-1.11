package net.halalaboos.huzuni.indev.script.meta;

import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.indev.script.Evaluation;
import net.halalaboos.huzuni.indev.script.SafeInvoker;

import javax.script.Invocable;

/**
 * Wrapper implementation of the Mod class. Used by scripts. <br/>
 * Created by Brandon Williams on 2/18/2017.
 */
public class ScriptMod extends Mod {

    private final SafeInvoker safeInvoker;

    public ScriptMod(Evaluation evaluation, String name, String description) {
        super(name, description);
        safeInvoker = new SafeInvoker((Invocable) evaluation.scriptEngine);
    }

    @Override
    protected void onToggle() {
        safeInvoker.safeInvoke("onToggle");
    }

    @Override
    protected void onEnable() {
        safeInvoker.safeInvoke("onEnable");
    }

    @Override
    protected void onDisable() {
        safeInvoker.safeInvoke("onDisable");
    }
}
