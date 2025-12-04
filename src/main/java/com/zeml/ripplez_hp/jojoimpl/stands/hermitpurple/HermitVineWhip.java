package com.zeml.ripplez_hp.jojoimpl.stands.hermitpurple;

import com.github.standobyte.jojo.mechanics.grab.LivingComponentGrab;
import com.github.standobyte.jojo.powersystem.Moveset;
import com.github.standobyte.jojo.powersystem.Power;
import com.github.standobyte.jojo.powersystem.PowerClass;
import com.github.standobyte.jojo.powersystem.ability.Ability;
import com.github.standobyte.jojo.powersystem.ability.AbilityId;
import com.github.standobyte.jojo.powersystem.ability.AbilityType;
import com.github.standobyte.jojo.powersystem.ability.AbilityUsageGroup;
import com.github.standobyte.jojo.powersystem.entityaction.ActionPhase;
import com.github.standobyte.jojo.powersystem.entityaction.EntityActionInstance;
import com.github.standobyte.jojo.powersystem.entityaction.LivingComponentAction;
import com.github.standobyte.jojo.powersystem.entityaction.type.EntityActionType;
import com.github.standobyte.jojo.powersystem.standpower.StandPower;
import com.zeml.ripplez_hp.core.HermitPurpleAddon;
import net.minecraft.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class HermitVineWhip extends HermitAction{

    public HermitVineWhip(AbilityType<?> abilityType, AbilityId abilityId) {
        super(abilityType, abilityId, HermitVine::new);
        usageGroup = AbilityUsageGroup.COMBAT;
        setDefaultPhaseLength(ActionPhase.WINDUP, 4);
        setDefaultPhaseLength(ActionPhase.PERFORM, 6);
        setDefaultPhaseLength(ActionPhase.RECOVERY, 5);

    }

    @Override
    public Ability replaceWithSubAbility(Power<?> context) {
        StandPower standPower = PowerClass.STAND.cast(context);
        if (standPower != null) {
            Moveset moveset = standPower.getMoveset();

            Ability punch = getVineCombo(context.getUser(), moveset);
            if (punch != null) return punch;
        }
        return super.replaceWithSubAbility(context);
    }

    @Deprecated
    protected List<String> punchNames = Util.make(new ArrayList<>(), list -> {
        list.add("hp_vine");
        list.add("hp_vine2");
        list.add("hp_vine3");
    });


    protected Ability getVineCombo(LivingEntity livingEntity, Moveset moveset){
        int startFromVine = 0;
        AbilityId curAbility = LivingComponentAction.getComponent(livingEntity).comboString.getLast();
        int size = punchNames.size();
        if (curAbility != null) {
            String actionName = curAbility.nameInMoveset();
            for (int i = 0; i < size; i++) {
                if (punchNames.get(i).equals(actionName)) {
                    startFromVine = i + 1;
                    break;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            int index = (startFromVine + i) % size;
            String nextPunchName = punchNames.get(index);
            Ability nextPunch = moveset.getAbility(nextPunchName);
            if (nextPunch != null) {
                return nextPunch;
            }
        }

        return null;
    }


    @Override
    public void initActionFromConfig(EntityActionInstance action, Level level, LivingEntity standUser, LivingEntity standEntity) {
        super.initActionFromConfig(action, level, standUser, standEntity);
    }


    public static class HermitVine extends EntityActionInstance{

        public HermitVine(EntityActionType ability) {
            super(ability);
        }
    }
}
