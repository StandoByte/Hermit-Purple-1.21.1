package com.zeml.ripplez_hp.jojoimpl.stands.hermitpurple.client.renderer;

import com.github.standobyte.jojo.client.ClientGlobals;
import com.github.standobyte.jojo.client.entityrender.parsemodel.loader.RotpGeckoModelLoader;
import com.github.standobyte.jojo.client.standskin.StandSkinsLoader;
import com.github.standobyte.jojo.mechanics.clothes.mannequin.MannequinEntity;
import com.github.standobyte.jojo.powersystem.standpower.StandPower;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.zeml.ripplez_hp.core.HermitPurpleAddon;
import com.zeml.ripplez_hp.init.power.AddonStands;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class HermitPurpleVinesLayer <T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    HermitPurpleVinesModel purpleModel;
    private final ResourceLocation HERMIT = ResourceLocation.tryBuild(HermitPurpleAddon.MOD_ID,"textures/entity/stand/hp_vine.png");
    public HermitPurpleVinesLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T t, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!ClientGlobals.canSeeStands || (t.isInvisible() && !(t instanceof MannequinEntity))){
            return;
        }

        if(StandPower.getOptional(t).isPresent() && StandPower.get(t).getPowerType() == AddonStands.HERMIT_PURPLE.get() && StandPower.get(t).isSummoned()){
            ResourceLocation hermit = HERMIT;

            ResourceLocation texture = StandSkinsLoader.getInstance().getSkin(StandPower.get(t)).getTexture(hermit);
            M parentModel = getParentModel();

            purpleModel = new HermitPurpleVinesModel(RotpGeckoModelLoader.getInstance().getModelDefinition(HermitPurpleAddon.resLoc("hermit_vines")).bakeRoot());

            parentModel.copyPropertiesTo(purpleModel);
            purpleModel.rightArm.copyFrom(parentModel.rightArm);
            VertexConsumer ivertexbuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
            purpleModel.renderToBuffer(poseStack,ivertexbuilder,packedLight, OverlayTexture.NO_OVERLAY);

        }
    }
}
