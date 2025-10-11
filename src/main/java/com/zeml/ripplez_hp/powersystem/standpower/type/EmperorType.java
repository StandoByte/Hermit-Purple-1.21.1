package com.zeml.ripplez_hp.powersystem.standpower.type;

import com.github.standobyte.jojo.init.ModItemDataComponents;
import com.github.standobyte.jojo.powersystem.MovesetBuilder;
import com.github.standobyte.jojo.powersystem.standpower.StandPower;
import com.github.standobyte.jojo.powersystem.standpower.StandStats;
import com.github.standobyte.jojo.powersystem.standpower.type.StandType;
import com.zeml.ripplez_hp.core.HermitPurpleAddon;
import com.zeml.ripplez_hp.init.AddonItems;
import com.zeml.ripplez_hp.init.HermitDataComponents;
import com.zeml.ripplez_hp.mc.item.component.EmperorGunData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;


public class EmperorType extends StandType {

    public EmperorType(StandStats stats, MovesetBuilder moveset, ResourceLocation id) {
        super(stats, moveset, id);
    }

    @Override
    public boolean summon(LivingEntity user, StandPower standPower) {
        HermitPurpleAddon.LOGGER.debug("Stand Skin {}, ", standPower.getSelectedSkin());
        ItemStack emperor = new ItemStack(AddonItems.EMPEROR.asItem());
        //emperor.set(ModItemDataComponents.ITEM_MODEL,ResourceLocation.withDefaultNamespace("diamond"));
        emperor.set(HermitDataComponents.EMPEROR.get(), new EmperorGunData(Optional.of(user.getUUID()), Optional.empty(),0));
        if(!user.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()){
            if(user.getItemInHand(InteractionHand.OFF_HAND).isEmpty()){
                user.setItemInHand(InteractionHand.OFF_HAND,emperor);
            }else {
                ItemStack itemStack = user.getItemInHand(InteractionHand.MAIN_HAND);
                ItemEntity item = new ItemEntity(user.level(),user.getX(),user.getY(),user.getZ(),itemStack);
                user.level().addFreshEntity(item);
                user.setItemInHand(InteractionHand.MAIN_HAND,emperor);
            }
        }else {
            user.setItemInHand(InteractionHand.MAIN_HAND,emperor);
        }
        return super.summon(user, standPower);
    }
}
