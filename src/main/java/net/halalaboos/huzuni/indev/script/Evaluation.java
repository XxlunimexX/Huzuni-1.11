package net.halalaboos.huzuni.indev.script;

/**
 * Created by Brandon Williams on 2/19/2017.
 */

import javax.script.ScriptEngine;

/**
 * Holds information regarding a scripts evaluation.
 * Created by Brandon Williams on 2/19/2017.
 * */
public class Evaluation {

    public final ScriptEngine scriptEngine;

    public final Object evaluation;

    public Evaluation(ScriptEngine scriptEngine, Object evaluation) {
        this.scriptEngine = scriptEngine;
        this.evaluation = evaluation;
    }
}