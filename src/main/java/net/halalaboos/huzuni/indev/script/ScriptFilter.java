package net.halalaboos.huzuni.indev.script;

import jdk.nashorn.api.scripting.ClassFilter;

/**
 * Default script filter. Used within the script manager. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ScriptFilter implements ClassFilter {

    // The scripts are somewhat 'sandboxed', meaning they're allowed to access only certain packages and files.
    private final String[] allowedPackages;

    // The scripts are also banned from accessing certain classes as well.
    private final String[] bannedClasses;


    public ScriptFilter(String[] allowedPackages, String[] bannedClasses) {
        this.allowedPackages = allowedPackages;
        this.bannedClasses = bannedClasses;
    }

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
}
