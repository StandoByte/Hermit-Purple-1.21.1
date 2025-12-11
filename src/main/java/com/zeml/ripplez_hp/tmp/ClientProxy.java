package com.zeml.ripplez_hp.tmp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ClientProxy {
	
	public static void openScreen(Object screen) {
		Minecraft.getInstance().setScreen((Screen) screen);
	}
}
