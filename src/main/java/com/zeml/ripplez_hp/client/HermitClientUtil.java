package com.zeml.ripplez_hp.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class HermitClientUtil {

    public static void vertex(PoseStack.Pose pose, VertexConsumer vertexConsumer,
                              int packedLight, int packedOverlay, float red, float green, float blue, float alpha,
                              float offsetX, float offsetY, float offsetZ,
                              float texU, float texV,
                              float normalX, float normalY, float normalZ) {

        vertexConsumer
                .addVertex(pose.pose(), offsetX, offsetY, offsetZ)
                .setColor(red, green, blue, alpha)
                .setUv(texU, texV)
                .setOverlay(packedOverlay)
                .setUv2(packedLight & 65535, packedLight >> 16 & 65535)
                .setNormal(pose, normalX, normalY, normalZ);
    }



}
