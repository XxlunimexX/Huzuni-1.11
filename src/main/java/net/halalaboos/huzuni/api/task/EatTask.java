package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.mcwrapper.api.item.types.Food;
import net.halalaboos.mcwrapper.api.item.ItemStack;

/**
 * Created by Brandon Williams on 3/1/2017.
 */
public abstract class EatTask extends HotbarTask {

    public EatTask(Nameable handler) {
        super(handler);
        addDependency("off_interact");
    }

    @Override
    public void onTaskCancelled() {

    }

    @Override
    protected boolean isValid(ItemStack itemStack) {
        // Filter out the food items.
        return itemStack.getItemType() instanceof Food && isValid(itemStack, (Food) itemStack.getItemType());
    }

    /**
     * @return True if the itemstack and food item is acceptable.
     * */
    protected abstract boolean isValid(ItemStack itemStack, Food food);

        @Override
    public void onPreUpdate() {

    }

    @Override
    public void onPostUpdate() {

    }

}
