package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.node.attribute.Dependent;
import net.halalaboos.huzuni.api.node.attribute.Nameable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon Williams on 3/3/2017.
 */
public abstract class BasicTask implements Task {

    protected final List<String> dependencies = new ArrayList<>();

    protected final Nameable handler;

    protected boolean running = false;

    public BasicTask(Nameable handler) {
        this.handler = handler;
    }

    @Override
    public void addDependencies(String... newDependencies) {
        for (String dependency : newDependencies) {
            addDependency(dependency);
        }
    }

    @Override
    public void addDependency(String dependency) {
        if (!depends(dependency))
            dependencies.add(dependency);
    }

    @Override
    public void removeDependency(String dependency) {
        dependencies.remove(dependency);
    }

    @Override
    public boolean depends(String dependency) {
        return dependencies.contains(dependency);
    }

    @Override
    public boolean conflicts(Dependent other) {
        for (String dependency : other.getDependencies()) {
            if (depends(dependency))
                return true;
        }
        return false;
    }

    @Override
    public String[] getDependencies() {
        return dependencies.toArray(new String[dependencies.size()]);
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public Nameable getHandler() {
        return handler;
    }
}
