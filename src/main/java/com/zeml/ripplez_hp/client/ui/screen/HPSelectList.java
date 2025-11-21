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

import static java.util.Comparator.comparing;

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
        this.updateFilteredEntries();
    }

    private void updateFilteredEntries(){
        if (!Objects.equals(this.filterText, "")){
            switch (this.mode){
                case 1:
                    this.originalList.removeIf(item->{
                        if(item instanceof HermitEntry hermitEntry){
                            if(hermitEntry.item instanceof UUID && this.minecraft.level != null){
                                return !this.minecraft.level.getPlayerByUUID((UUID) hermitEntry.item).getName().getString().toLowerCase().contains(this.filterText);
                            }
                        }
                        return true;
                    });
                    break;
                case 2:
                    String entity = "entity.";
                    this.originalList.removeIf(item->{
                        if(item instanceof HermitEntry hermitEntry){
                            return !Component.translatable(entity.concat(hermitEntry.toString().replace(":","."))).toString().toLowerCase().contains(this.filterText);
                        }
                        return true;
                    });
                    break;
                case 3:
                    String stand = "stand.";
                    this.originalList.removeIf(item->{
                        if(item instanceof HermitEntry hermitEntry){
                            return !Component.translatable(stand.concat(hermitEntry.toString().replace(":","."))).toString().toLowerCase().contains(this.filterText);
                        }
                        return true;
                    });
                    break;
                case 5:
                    String biome = "biome.";
                    this.originalList.removeIf(item->{
                        if(item instanceof HermitEntry hermitEntry){
                            return !Component.translatable(biome.concat(hermitEntry.toString().replace(":","."))).toString().toLowerCase().contains(this.filterText);
                        }
                        return true;
                    });
                    break;
                default:
                    this.originalList.removeIf(item->{
                        if(item instanceof HermitEntry hermitEntry){
                            return !(item.toString().split(":")[1]).replace("_"," ").toLowerCase().contains(this.filterText);
                        }
                        return true;
                    });
                    break;
            }
        }
    }

    public void setFilter(String filter) {
        this.filterText = filter.toLowerCase();
        HermitPurpleAddon.getLogger().debug("Filtro?");
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        HermitPurpleAddon.getLogger().debug("Clicked {}", button);
        return super.mouseClicked(mouseX, mouseY, button);
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
            //HermitPurpleAddon.getLogger().debug("{} we are the world, we are the {} {}", item.toString() ,children.size(), children.contains(this.hpButton));
        }

        private String getPlayerName(){
            if(this.mode == 0 && item instanceof UUID){
                if(minecraft.level != null && minecraft.level.getPlayerByUUID((UUID) item) != null){
                    return minecraft.level.getPlayerByUUID((UUID) item).getName().toString();
                }
            }
            return "";
        }

        private void onButtonClick(){
            HermitPurpleAddon.getLogger().debug("Packet Sent {} {}", item.toString(),this.mode);
            PacketDistributor.sendToServer(new SetTargetPacket(this.mode,item.toString()));
        }
    }

       /*
    private void sortEntries(){
        switch (mode){
            case 0:
                originalList.sort(Comparator.comparing(entry -> {
                    if(entry instanceof HermitEntry){
                        HermitEntry hermitEntry = (HermitEntry)  entry;
                        if(hermitEntry.item instanceof UUID && this.minecraft.player != null){
                            if((hermitEntry.item).equals(this.minecraft.player.getUUID())){
                                return 0;
                            }
                        }
                    }
                    return 4;
                }));
                break;
        }
    }
     */


    //mouse clicked not working
}
