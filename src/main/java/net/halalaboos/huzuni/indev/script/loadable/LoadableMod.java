package net.halalaboos.huzuni.indev.script.loadable;

/**
 * Loads mod files. <br/>
 * Created by Brandon Williams on 2/19/2017.
public class LoadableMod extends LoadableScript {

    private final String name, description;

    private ScriptMod mod;

    public LoadableMod(String tag, String name, String description, Huzuni huzuni, ScriptManager scriptManager, Reader reader) throws ScriptException {
        super(tag, huzuni, scriptManager, reader);
        this.name = name;
        this.description = description;
    }

    @Override
    public void loadGlobals(ScriptEngine engine) {
        engine.put("eventManager", huzuni.eventManager);
    }

    @Override
    public void load(Evaluation evaluation) {
        mod = new ScriptMod(evaluation, name, description);
        huzuni.modManager.addMod(mod);
    }

    @Override
    public void destroy() {
        huzuni.modManager.removeMod(mod);
    }
}

 */