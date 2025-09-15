package com.zeml.ripplez_hp.init;

import com.zeml.ripplez_hp.RipplesAddon;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AddonSoundEvents {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, RipplesAddon.MOD_ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> EXAMPLE_SOUND = SOUNDS.register("example", SoundEvent::createVariableRangeEvent);
}
