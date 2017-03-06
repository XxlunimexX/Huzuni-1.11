package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.item.types.Food;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemFood.class)
public abstract class MixinItemFood extends MixinItem implements Food {

    @Final @Shadow private int healAmount;
    @Final @Shadow private float saturationModifier;

    @Override
    public float getSaturation() {
        return saturationModifier;
    }

    @Override
    public int getHealth() {
        return healAmount;
    }

    @Override
    public boolean satisfiesHunger(Player player) {
        return player.isHungry();
    }
}
