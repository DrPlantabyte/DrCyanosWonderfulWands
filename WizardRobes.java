package cyano.wizardrobes;


import cyano.wizardrobes.CommonProxy;
import cyano.wizardrobes.client.*;

import java.util.Map;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;

@Mod(modid="wizardrobes", name="Cyano's Wizarding Robes", version="1.1.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class WizardRobes {
	
	// wizard robes have the protection of leather (actually a little worse), the durability of iron, and the enchantibiliity of gold (actually a little better) 
	
	
	
	public static Item robesHead = null;
	public static Item robesChest = null;
	public static Item robesLegs = null;
	public static Item robesFeet = null;
	public static Item witchHat = null;
	public static Item wizardHat = null;
	public static Item topHat = null;
	
	
	// The instance of your mod that Forge uses.
	@Instance("wizardrobes")
	public static WizardRobes instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="cyano.wizardrobes.client.ClientProxy", serverSide="cyano.wizardrobes.CommonProxy")
	public static CommonProxy proxy;
	
	// Mark this method for receiving an FMLEvent (in this case, it's the FMLPreInitializationEvent)
    @EventHandler public void preInit(FMLPreInitializationEvent event)
    {
        // Do stuff in pre-init phase (read config, create blocks and items, register them)
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	
    //	renderIndex = config.get("misc", "armorRenderIndex", renderIndex).getInt();
    	
		int defaultEnum = 700;
		
		robesHead = new WizardingArmorHead(config.getItem("itemID_wizardingRobes_head", getNextItemID(defaultEnum++)).getInt(),proxy.getArmorRenderIndex("wizardingRobes"));
		robesChest = new WizardingArmorChest(config.getItem("itemID_wizardingRobes_chest", getNextItemID(defaultEnum++)).getInt(),proxy.getArmorRenderIndex("wizardingRobes"));
		robesLegs = new WizardingArmorLegs(config.getItem("itemID_wizardingRobes_legs", getNextItemID(defaultEnum++)).getInt(),proxy.getArmorRenderIndex("wizardingRobes"));
		robesFeet = new WizardingArmorFeet(config.getItem("itemID_wizardingRobes_feet", getNextItemID(defaultEnum++)).getInt(),proxy.getArmorRenderIndex("wizardingRobes"));
		
		wizardHat = new WizardsHat(config.getItem("itemID_wizardsHat", getNextItemID(defaultEnum++)).getInt(),proxy.getArmorRenderIndex("wizHat"));
		witchHat = new WitchsHat(config.getItem("itemID_witchsHat", getNextItemID(defaultEnum++)).getInt(),proxy.getArmorRenderIndex("wizHat"));
		topHat = new TopHat(config.getItem("itemID_tophat", getNextItemID(defaultEnum++)).getInt(),proxy.getArmorRenderIndex("wizHat"));
		
		config.save();
    }
    
    private int getNextItemID(int startingID){
		int i = startingID;
		while(Item.itemsList[i] != null){
			i++;
			if(i >= Item.itemsList.length){
				throw new IndexOutOfBoundsException("No open Item ID numbers available above " + startingID);
			}
		}
		return i;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderers();

		if (event.getSide().isClient())
			ClientProxy.setCustomRenderers();


		robesHead.setUnlocalizedName("robesHead");
		LanguageRegistry.addName(robesHead, "Wizard Hood");
		robesChest.setUnlocalizedName("robesChest");
		LanguageRegistry.addName(robesChest, "Wizard Robes");
		robesLegs.setUnlocalizedName("robesLegs");
		LanguageRegistry.addName(robesLegs, "Wizard Leggings");
		robesFeet.setUnlocalizedName("robesFeet");
		LanguageRegistry.addName(robesFeet, "Wizard Slippers");
		

		wizardHat.setUnlocalizedName("wizardHat");
		LanguageRegistry.addName(wizardHat, "Wizard's Hat");
		witchHat.setUnlocalizedName("witchHat");
		LanguageRegistry.addName(witchHat, "Witch's Hat");
		topHat.setUnlocalizedName("topHat");
		LanguageRegistry.addName(topHat, "Top Hat");
		

		

		// add crafting recipes (3X3 shaped)
		// Item stack (ID, Count, Meta)
		ItemStack cloth = new ItemStack(Block.cloth);
		cloth.setItemDamage(10);
		ItemStack gold = new ItemStack(Item.ingotGold);
		ItemStack diamond = new ItemStack(Item.diamond);
		ItemStack bluecloth = new ItemStack(Block.cloth);
		bluecloth.setItemDamage(11);
		ItemStack blackcloth = new ItemStack(Block.cloth);
		blackcloth.setItemDamage(15);
		ItemStack leather = new ItemStack(Item.leather);
		ItemStack output;

		
		// recipes
		output = new ItemStack(robesHead);
		GameRegistry.addRecipe(output, "ccc", "cgc",  'c', cloth, 'g', gold);
		
		output = new ItemStack(robesChest);
		GameRegistry.addRecipe(output, "cgc", "ccc", "ccc", 'c', cloth, 'g', gold);
		
		output = new ItemStack(robesLegs);
		GameRegistry.addRecipe(output, "cgc", "c c", "c c", 'c', cloth, 'g', gold);
		
		output = new ItemStack(robesFeet);
		GameRegistry.addRecipe(output, "c c", "g g", 'c', cloth, 'g', gold);

		output = new ItemStack(wizardHat);
		GameRegistry.addRecipe(output, " d "," b ", "bbb", 'b', bluecloth, 'd', diamond);

		output = new ItemStack(witchHat);
		GameRegistry.addRecipe(output, " d "," b ", "bbb", 'b', blackcloth, 'd', diamond);

		output = new ItemStack(topHat);
		GameRegistry.addRecipe(output, " b ", " l ", 'b', blackcloth, 'l',leather);

	}
    
   
	/*
	@EventHandler public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
	
	@EventHandler public void onServerStarting(FMLServerStartingEvent event)
	{
		// stub
	}
	*/
}
