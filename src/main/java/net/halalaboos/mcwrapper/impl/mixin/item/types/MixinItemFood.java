package net.halalaboos.mcwrapper.impl.mixin.item.types;

import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.item.types.Food;
import net.halalaboos.mcwrapper.impl.mixin.item.MixinItem;
import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemFood.class)
public abstract class MixinItemFood extends MixinItem implements Food {

    @Shadow
    private int healAmount;

    @Shadow
    private float saturationModifier;

    //@Shadow
    //public abstract float getSaturationModifier(ItemStack itemStack);

    //@Shadow
    //public abstract int getHealAmount(ItemStack itemStack);

    @Override
    public float getSaturation(net.halalaboos.mcwrapper.api.item.ItemStack itemStack) {
        return saturationModifier;
    }

    @Override
    public int getHealth(net.halalaboos.mcwrapper.api.item.ItemStack itemStack) {
        return healAmount;
    }


    @Override
    public boolean satisfiesHunger(Living living, net.halalaboos.mcwrapper.api.item.ItemStack itemStack) {
        return (living.getHealthData().getMaxHealth() - living.getHealthData().getCurrentHealth()) <= getHealth(itemStack);
    }
}
