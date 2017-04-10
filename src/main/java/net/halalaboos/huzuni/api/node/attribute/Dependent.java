package net.halalaboos.huzuni.api.node.attribute;

/**
 * Created by Brandon Williams on 3/3/2017.
 */
public interface Dependent {

    void addDependencies(String... dependencies);

    void addDependency(String dependency);

    void removeDependency(String dependency);

    /**
     * @return True if this dependent relies on the given dependency.
     * */
    boolean depends(String dependency);

    /**
     * @return True if this dependent has conflicting dependencies as the given dependent.
     * */
    boolean conflicts(Dependent other);

    String[] getDependencies();
}