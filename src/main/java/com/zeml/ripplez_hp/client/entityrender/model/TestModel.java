package com.zeml.ripplez_hp.client.entityrender.model;

import com.github.standobyte.jojo.client.entityanim.playerbend.IPlayerBendModel;
import com.github.standobyte.v1_21_4_stuff.Reminder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.client.model.geom.ModelPart;

import java.util.ArrayList;
import java.util.HashMap;


public class TestModel extends HumanoidModel {

    private static final String[] BASE_HUMANOID_PARTS = new String[] { "head", "body", "right_arm", "left_arm", "right_leg", "left_leg", "right_arm_slim", "left_arm_slim" };
    protected static ModelPart addMissing(ModelPart root) {
        for (String basePartName : BASE_HUMANOID_PARTS) {
            root.children.putIfAbsent(basePartName, new ModelPart(new ArrayList<>(), new HashMap<>()));

        }
        Reminder.thatHatIsHeadChildNow();
        root/*.getChild("head")*/.children.putIfAbsent("hat", new ModelPart(new ArrayList<>(), new HashMap<>()));
        return root;
    }
    public TestModel(ModelPart root){
        super(addMissing(root));
        IPlayerBendModel thisBends = (IPlayerBendModel) this;

    }


    @Override
    public void setupAnim(LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {

    }
}
