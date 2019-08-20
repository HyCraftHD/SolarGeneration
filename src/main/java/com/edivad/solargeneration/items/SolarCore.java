package com.edivad.solargeneration.items;

import com.edivad.solargeneration.Main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SolarCore extends Item{
	
	public SolarCore(String levelCore) {
		
		setRegistryName(new ResourceLocation(Main.MODID, levelCore));
		setUnlocalizedName(Main.MODID + "." + levelCore);
        setCreativeTab(Main.solarGenerationTab);
		setMaxStackSize(64);
	}

	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
