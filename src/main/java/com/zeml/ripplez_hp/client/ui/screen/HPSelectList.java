package com.zeml.ripplez_hp.client.ui.screen;

import com.google.common.collect.ImmutableList;
import com.zeml.ripplez_hp.core.HermitPurpleAddon;
import com.zeml.ripplez_hp.core.packets.client.SetTargetPacket;
import com.zeml.ripplez_hp.init.AddonDataAttachmentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class HPSelectList extends ContainerObjectSelectionList<HPSelectList.HermitEntry> {
    private final Minecraft minecraft;
    private List<Object> originalList;
    private String filterText = "";
    private int mode = 0;
    public HPSelectList(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
        this.minecraft = minecraft;
        this.originalList= new ArrayList<>();

    }



    public void updateList(Collection<?> newList){
        this.clearEntries();
        this.originalList = new ArrayList<>(newList);
        newList.forEach(item ->this.addEntry(new HermitEntry(item, getMode())));
    }

    private void updateFilteredEntries(){
        if (!Objects.equals(this.filterText, "")){

        }
    }

    public void setFilter(String filter) {
        this.filterText = filter.toLowerCase();

    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }

    public boolean isEmpty() {
        return this.children().isEmpty();
    }


    @Override
    protected void renderListBackground(GuiGraphics guiGraphics) {
        return;
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {
    }

    @OnlyIn(Dist.CLIENT)
    public class HermitEntry extends ContainerObjectSelectionList.Entry<HermitEntry>{
        protected static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.withDefaultNamespace("widget/button"), ResourceLocation.withDefaultNamespace("widget/button_disabled"), ResourceLocation.withDefaultNamespace("widget/button_highlighted"));
        private final Object item;
        private final ImageButton hpButton;
        private final int mode;
        private final List<AbstractWidget> children;

        public HermitEntry(Object item, int mode){
            this.item = item;
            this.mode = mode;
            this.hpButton = new ImageButton(0, 0, 20, 20,SPRITES,(ctx)->{
                HermitPurpleAddon.getLogger().debug("Packet Sent {} {}", item.toString(),this.mode);
                onButtonClick();
            });
            this.children = new ArrayList<>();
            this.children.add(this.hpButton);
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return this.children;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.children;
        }

        @Override
        public void render(GuiGraphics guiGraphics,int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            int i = left + 4;
            int j = top + (height - 24) / 2;
            int k = i + 24 + 4;
            int l = top + (height - 18) / 2;
            switch (this.mode){
                case 1:
                    if(item instanceof UUID){
                        guiGraphics.drawString(minecraft.font, minecraft.level.getPlayerByUUID((UUID) item).getName(), k, l + 12,0xFFFFFF,false);
                    }
                    break;
                case 2:
                    String entity = "entity.";
                    entity = entity.concat(item.toString().replace(":","."));
                    guiGraphics.drawString(minecraft.font, Component.translatable(entity), k, l + 12,0xFFFFFF,false);
                    break;
                case 3:
                    String stand = "stand.";
                    stand = stand.concat(item.toString().replace(":","."));
                    guiGraphics.drawString(minecraft.font,Component.translatable(stand) , k, l + 12,0xFFFFFF,false);
                    break;
                case 5:
                    String biome = "biome.";
                    biome = biome.concat(item.toString().replace(":","."));
                    guiGraphics.drawString(minecraft.font,Component.translatable(biome) , k, l + 12,0xFFFFFF,false);
                    break;
                default:
                    String data = item.toString().split(":")[1];
                    data = data.replace("_"," ");
                    guiGraphics.drawString(minecraft.font,data , k, l + 12,0xFFFFFF,false);
                    break;
            }
            if(this.hpButton != null){
                this.hpButton.setX(left + (width - this.hpButton.getWidth() - 4));
                this.hpButton.setY(top + (height - this.hpButton.getHeight()) / 2);
                this.hpButton.render(guiGraphics, mouseX, mouseY, partialTick);
            }

        }


        private void onButtonClick(){
            HermitPurpleAddon.getLogger().debug("Packet Sent {} {}", item.toString(),this.mode);
            PacketDistributor.sendToServer(new SetTargetPacket(this.mode,item.toString()));
        }
    }
}
