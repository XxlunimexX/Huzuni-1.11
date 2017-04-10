package net.halalaboos.huzuni.mod.combat;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.ItemSelector;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.task.EatTask;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.types.Food;

/**
 * Created by Brandon Williams on 2/28/2017.
 */
public class Autoeat extends BasicMod {

    public final ItemSelector<Food> foodSelector = new ItemSelector<>("Food", "Select which food to automagically eat.");

    public final Toggleable smartEat = new Toggleable("Smart Eat", "Eat only when the food items satisfy hunger.");

    private final EatTask eatTask = new EatTask(this) {
        @Override
        protected boolean isValid(ItemStack itemStack, Food food) {
            return foodSelector.getItemDatas().stream().anyMatch(foodItemData -> {
                for (Food type : foodItemData.getTypes())
                    if (type == food)
                        return true;
                return false;
            });
        }
    };

    public Autoeat() {
        super("Auto eat", "");
        addChildren(foodSelector, smartEat);
        setCategory(Category.COMBAT);
        setAuthor("Halalaboos");
        // for (Food food : get the food){
        //     foodSelector.addItem(ItemStack.from(food, 1), food);
        // }
    }
}
