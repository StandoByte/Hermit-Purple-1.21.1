package com.zeml.ripplez_hp.init.power;

import com.github.standobyte.jojo.core.JojoRegistries;
import com.github.standobyte.jojo.powersystem.MovesetBuilder;
import com.github.standobyte.jojo.powersystem.ability.controls.InputKey;
import com.github.standobyte.jojo.powersystem.ability.controls.InputMethod;
import com.github.standobyte.jojo.powersystem.standpower.StandStats;
import com.github.standobyte.jojo.powersystem.standpower.entity.EntityStandType;
import com.github.standobyte.jojo.powersystem.standpower.type.StandType;

import com.zeml.ripplez_hp.RipplesAddon;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AddonStands {
	public static final DeferredRegister<StandType> STANDS = DeferredRegister.create(JojoRegistries.DEFAULT_STANDS_REG, RipplesAddon.MOD_ID);
	
	public static final DeferredHolder<StandType, EntityStandType> EXAMPLE = STANDS.register(
			"hermit_purple", id ->
			new EntityStandType(
					new StandStats.Builder()
					.power(6)
					.speed(10)
					.range(3, 3)
					.durability(14)
					.precision(7)
					.build(),

					new MovesetBuilder()

					.addHumanoidStandStuff()
					.makeHotbar(0, InputKey.X, InputKey.C)
					
					.addAbility("placeholder1", AddonStandAbilities._PLACEHOLDER)
					.inHotbar(0, InputMethod.CLICK)
					
					.addAbility("placeholder2", AddonStandAbilities._PLACEHOLDER)
					.inHotbar(0, InputMethod.CLICK)
					
					.addAbility("placeholder3", AddonStandAbilities._PLACEHOLDER)
					.inHotbar(0, InputMethod.CLICK)
					
					.addAbility("placeholder4", AddonStandAbilities._PLACEHOLDER)
					.inHotbar(0, InputMethod.CLICK)
					
					.addAbility("placeholder5", AddonStandAbilities._PLACEHOLDER)
					.inHotbar(0, InputMethod.CLICK)
					
					.addAbility("placeholder6", AddonStandAbilities._PLACEHOLDER)
					.inHotbar(0, InputMethod.CLICK)

					, id));
}
