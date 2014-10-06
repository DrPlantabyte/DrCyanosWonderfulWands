package cyano.wonderfulwands;


import cyano.wonderfulwands.blocks.MageLight;
import cyano.wonderfulwands.graphics.MagicMissileRenderer;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;
import cyano.wonderfulwands.wands.*;

import java.util.Map;
import java.util.HashMap;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = WonderfulWands.MODID, name=WonderfulWands.NAME, version = WonderfulWands.VERSION)
public class WonderfulWands {
    public static final String MODID = "wonderfulwands";
    public static final String NAME ="Cyano's Wonderful Wands";
    public static final String VERSION = "1.3.0";
	
    @SidedProxy(clientSide="cyano.wonderfulwands.ClientProxy", serverSide="cyano.wonderfulwands.ServerProxy")
    public static Proxy proxy;
    
	public static Wand wandGeneric = null;
	public static Wand wandOfMagicMissile = null;
	public static Wand wandOfDeath = null;
	public static Wand wandOfFire = null;
	public static Wand wandOfGrowth = null;
	public static Wand wandOfHarvesting = null;
	public static Wand wandOfHealing = null;
	public static Wand wandOfIce = null;
	public static Wand wandOfMining = null;
	public static Wand wandOfTeleportation = null;
	public static Wand wandOfLight = null;
	public static Wand wandOfStorms = null;
	public static Wand wandOfLightning = null;
	
	public static Block mageLight = null;
	
	
	// Mark this method for receiving an FMLEvent (in this case, it's the FMLPreInitializationEvent)
    @EventHandler public void preInit(FMLPreInitializationEvent event)
    {
        // Do stuff in pre-init phase (read config, create blocks and items, register them)
    	// load config
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
		
		
		wandGeneric = new OrdinaryWand();
		wandOfMagicMissile = new WandOfMagicMissile();
		wandOfFire = new WandOfFire();
		wandOfDeath = new WandOfDeath();
		wandOfGrowth = new WandOfGrowth();
		wandOfHarvesting = new WandOfHarvesting();
		wandOfHealing = new WandOfHealing();
		wandOfIce = new WandOfIce();
		wandOfMining = new WandOfMining();
		wandOfTeleportation = new WandOfTeleportation();
		wandOfLight = new WandOfLight();
		wandOfStorms = new WandOfStorms();
		
		mageLight = new MageLight();
		GameRegistry.registerBlock(mageLight, MageLight.name);

		// Register the wand items
		GameRegistry.registerItem(wandGeneric, OrdinaryWand.itemName);
		GameRegistry.registerItem(wandOfMagicMissile, WandOfMagicMissile.itemName);
		GameRegistry.registerItem(wandOfFire, WandOfFire.itemName);
		GameRegistry.registerItem(wandOfDeath, WandOfDeath.itemName);
		GameRegistry.registerItem(wandOfGrowth, WandOfGrowth.itemName);
		GameRegistry.registerItem(wandOfHarvesting, WandOfHarvesting.itemName);
		GameRegistry.registerItem(wandOfHealing, WandOfHealing.itemName);
		GameRegistry.registerItem(wandOfIce, WandOfIce.itemName);
		GameRegistry.registerItem(wandOfMining, WandOfMining.itemName);
		GameRegistry.registerItem(wandOfTeleportation, WandOfTeleportation.itemName);
		GameRegistry.registerItem(wandOfLight, WandOfLight.itemName);
		GameRegistry.registerItem(wandOfStorms, WandOfStorms.itemName);
		
		// recipes

		// add crafting recipes (3X3 shaped)
		// Item stack (ID, Count, Meta)
		ItemStack x;
		ItemStack output;

		// Nonmagical
		GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(wandGeneric), " g ", " s ", " g ", 'g', "nuggetGold", 's',
				"stickWood"));
		// Magic Missile
		addWandRecipe(wandOfMagicMissile,Items.golden_sword);
		// Fire
		addWandRecipe(wandOfFire,Items.fire_charge);;
		// Death
		addWandRecipe(wandOfDeath,new ItemStack(Items.skull,1,1));
		// Growth
		addWandRecipe(wandOfGrowth,Items.bone);
		// Harvesting
		addWandRecipe(wandOfHarvesting,Items.shears);
		// Healing
		addWandRecipe(wandOfHealing,Items.ghast_tear);
		// Ice
		addWandRecipe(wandOfIce,Items.snowball);
		// Digging
		addWandRecipe(wandOfMining,Items.golden_pickaxe);
		// Teleport
		addWandRecipe(wandOfTeleportation, Items.ender_eye);
		// wand of light
		addWandRecipe(wandOfLight,"dustGlowstone");
		
		
	//	OreDictionary.initVanillaEntries()
		config.save();
		proxy.preInit(event);
    }

    private static void addWandRecipe(Wand output, Item specialItem){
    	GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
    }

    private static void addWandRecipe(Wand output, ItemStack specialItem){
    	GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
    }
    
    private static void addWandRecipe(Wand output, String specialItem_oreDictionary){
    	GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem_oreDictionary, 'e',
				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
    }
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// register entities
		EntityRegistry.registerGlobalEntityID(EntityMagicMissile.class, "magic_missile", EntityRegistry.findGlobalUniqueEntityId());
 		EntityRegistry.registerModEntity(EntityMagicMissile.class, "magic_missile", 0/*id*/, this, 128/*trackingRange*/, 1/*updateFrequency*/, true/*sendsVelocityUpdates*/);
 		
		proxy.init(event);
	}
    
    public static ItemStack wandItemStack(Wand w){
    	ItemStack i = new ItemStack(w);
    	i.setRepairCost(w.getBaseRepairCost());
    	return i;
    }
	
	@EventHandler public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	/*
	@EventHandler public void onServerStarting(FMLServerStartingEvent event)
	{
		// stub
	}
	*/
}
