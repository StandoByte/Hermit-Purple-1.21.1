package com.zeml.ripplez_hp.mixin;

import com.github.standobyte.jojo.client.entityrender.RipplesPlayerRenderState;
import com.github.standobyte.jojo.client.entityrender.stand.StandEntityRenderState;
import com.github.standobyte.jojo.client.standskin.StandSkin;
import com.github.standobyte.jojo.client.standskin.StandSkinsScreen;
import com.github.standobyte.jojo.mechanics.clothes.mannequin.MannequinEntity;
import com.github.standobyte.jojo.powersystem.standpower.StandPower;
import com.github.standobyte.jojo.powersystem.standpower.entity.EntityStandType;
import com.github.standobyte.jojo.powersystem.standpower.type.StandType;
import com.github.standobyte.jojo.powersystem.standpower.type.SummonedStand;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.zeml.ripplez_hp.core.HermitPurpleAddon;
import com.zeml.ripplez_hp.init.AddonItems;
import com.zeml.ripplez_hp.init.power.AddonStands;
import com.zeml.ripplez_hp.powersystem.standpower.type.EmperorType;
import com.zeml.ripplez_hp.powersystem.standpower.type.HermitPurpleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(value = StandSkinsScreen.SkinView.class,remap = false)
public abstract class HPStandSkinsScreenMixin  {


    @Shadow public StandType standType;
    @Shadow public StandSkin skin;

    @Inject(method = "Lcom/github/standobyte/jojo/client/standskin/StandSkinsScreen$SkinView;renderStand(Lnet/minecraft/client/gui/GuiGraphics;IIFZFFFFFFFF)V",
    at = @At("TAIL"), cancellable = true)
    private void onRenderStand(GuiGraphics gui, int mouseX, int mouseY, float ticks, boolean isHovered,
                               float posX, float posY, float scale, float scaleZoom,
                               float yRot, float xRot, float xOffsetRatio, float yOffsetRatio, CallbackInfo ci){
        if(this.standType instanceof HermitPurpleType hermitPurpleType){
            onRenderHermit(gui,posX,posY,scale,scaleZoom, yRot, xRot, xOffsetRatio, yOffsetRatio, hermitPurpleType,this.skin,ticks);
        }
        if(standType instanceof EmperorType emperorType){
            onRenderEmperor(gui,posX,posY,scale,scaleZoom, yRot, xRot, xOffsetRatio, yOffsetRatio, emperorType,this.skin,ticks);
        }

    }

    private static <S extends RipplesPlayerRenderState> void onRenderHermit(GuiGraphics gui, float posX, float posY, float scale, float scaleZoom, float yRot, float xRot, float xOffsetRatio, float yOffsetRatio, HermitPurpleType standType, StandSkin standSkin, float ticks){
        Quaternionf rotation = (new Quaternionf()).rotateX(-xRot).rotateY(-yRot);
        gui.pose().pushPose();
        gui.pose().translate(posX, posY, 350.0);
        gui.pose().translate(xOffsetRatio, yOffsetRatio, 0.0F);
        gui.pose().scale(scale, -scale, scale);
        gui.pose().translate(0.0, 1.25, 0.0);
        gui.pose().scale(scaleZoom, scaleZoom, scaleZoom);
        gui.pose().mulPose(rotation);
        gui.pose().translate(0.0, -1.25, 0.0);
        gui.flush();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
        renderManager.setRenderShadow(false);



        Zombie zombie = new Zombie(Minecraft.getInstance().level);

        StandPower.getOptional(zombie).ifPresent(standPower -> {
            standPower.setStand(AddonStands.HERMIT_PURPLE.get());
            standPower.setSummonedStand( new SummonedStand.BlankSummonedStand());
            standPower.setSelectedSkin(Optional.of(standSkin.skinId));
        });

        RenderSystem.runAsFancy(()->{

            renderManager.render(zombie, 0.0, 0.0, 0.0, 0.0F, ticks,gui.pose(),gui.bufferSource(), 15728880);
        });
        gui.flush();
        renderManager.setRenderShadow(true);
        gui.pose().popPose();
        Lighting.setupFor3DItems();

    }

    private static void onRenderEmperor(GuiGraphics gui, float posX, float posY, float scale, float scaleZoom, float yRot, float xRot, float xOffsetRatio, float yOffsetRatio, EmperorType standType, StandSkin standSkin, float ticks){
        Quaternionf rotation = (new Quaternionf()).rotateX(-xRot).rotateY(-yRot+.75F);
        gui.pose().pushPose();
        gui.pose().translate(posX-25, posY, 350.0);
        gui.pose().translate(xOffsetRatio, yOffsetRatio, 0.0F);
        gui.pose().scale(scale, -scale, scale);
        gui.pose().translate(0.0, 1.25, 0.0);
        gui.pose().scale(scaleZoom*2, scaleZoom*2, scaleZoom*2);
        gui.pose().mulPose(rotation);
        gui.pose().translate(0.0, -1.25, 0.0);
        gui.flush();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
        renderManager.setRenderShadow(false);

        Zombie zombie = new Zombie(Minecraft.getInstance().level);
        zombie.setItemInHand(InteractionHand.MAIN_HAND,AddonItems.EMPEROR.toStack());
        zombie.setInvisible(true);
        zombie.setNoAi(true);

        RenderSystem.runAsFancy(()->{
            renderManager.render(zombie, 0.0, 0.0, 0.0, 0.0F, ticks,gui.pose(),gui.bufferSource(), 15728880);
        });
        gui.flush();
        renderManager.setRenderShadow(true);
        gui.pose().popPose();
        Lighting.setupFor3DItems();

    }

}
