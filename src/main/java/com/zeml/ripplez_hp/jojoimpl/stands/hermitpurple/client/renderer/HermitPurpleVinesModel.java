package com.zeml.ripplez_hp.jojoimpl.stands.hermitpurple.client.renderer;

import com.github.standobyte.v1_21_4_stuff.Reminder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

import java.util.ArrayList;
import java.util.HashMap;

public class HermitPurpleVinesModel extends HumanoidModel {
    public final ModelPart vines;
    public final ModelPart grab_vine;
    private static final String[] BASE_HUMANOID_PARTS = new String[] { "head", "body", "right_arm", "left_arm", "right_leg", "left_leg", "right_arm_slim", "left_arm_slim" };
    protected static ModelPart addMissing(ModelPart root) {
        for (String basePartName : BASE_HUMANOID_PARTS) {
            root.children.putIfAbsent(basePartName, new ModelPart(new ArrayList<>(), new HashMap<>()));
        }
        Reminder.thatHatIsHeadChildNow();
        root/*.getChild("head")*/.children.putIfAbsent("hat", new ModelPart(new ArrayList<>(), new HashMap<>()));
        return root;
    }

    public HermitPurpleVinesModel(ModelPart root) {
        super(addMissing(root));
        root.children.putIfAbsent("hidden#vines",new ModelPart(new ArrayList<>(), new HashMap<>()));
        this.vines = root.getChild("hidden#vines");

        root.children.putIfAbsent("hidden#grab_vine", new ModelPart(new ArrayList<>(), new HashMap<>()));
        this.grab_vine = root.getChild("hidden#grab_vine");
    }
}
