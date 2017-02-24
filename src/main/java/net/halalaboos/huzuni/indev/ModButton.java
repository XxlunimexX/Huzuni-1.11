package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.indev.gui.components.Button;

/**
 * Button implementation which will toggle a given mod. It's highlight will reflect the state of it's mod.<br/>
 * Created by Brandon Williams on 2/16/2017.
 */
public class ModButton extends Button {

    private final GuiTestScreen guiScreen;

    private final Mod mod;

    public ModButton(Mod mod, GuiTestScreen guiScreen) {
        super("mod", mod.getName(), mod.isEnabled());
        this.mod = mod;
        this.guiScreen = guiScreen;
    }

    @Override
    public void onPressed() {
        super.onPressed();
        guiScreen.loadMod(mod);
    }

    public Mod getMod() {
        return mod;
    }

    @Override
    public boolean isHighlight() {
        return mod.isEnabled();
    }
}
