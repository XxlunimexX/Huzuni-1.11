package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;

import java.io.File;

/**
 * Created by Brandon Williams on 2/18/2017.
 */
public class RunScript extends BasicCommand {

    public RunScript() {
        super("runscript", "Runs a given script name");
    }

    @Override
    protected void runCommand(String input, String[] args) throws Exception {
        huzuni.scriptManager.loadScript(new File(huzuni.getSaveFolder(), args[0]));
        huzuni.addChatMessage("Running script: " + args[0]);
    }
}
