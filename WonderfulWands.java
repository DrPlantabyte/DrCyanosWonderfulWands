package cyano.wonderfulwands;


import cyano.wonderfulwands.CommonProxy;
import cyano.wonderfulwands.client.*;
import cyano.wonderfulwands.wands.*;

import java.util.Map;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
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

@Mod(modid="wonderfulwands", name="Cyano's Wonderful Wands", version="1.1.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class WonderfulWands {
	
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
	
	// The instance of your mod that Forge uses.
	@Instance("wonderfulwands")
	public static WonderfulWands instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="cyano.wonderfulwands.client.ClientProxy", serverSide="cyano.wonderfulwands.CommonProxy")
	public static CommonProxy proxy;
	
	// Mark this method for receiving an FMLEvent (in this case, it's the FMLPreInitializationEvent)
    @EventHandler public void preInit(FMLPreInitializationEvent event)
    {
        // Do stuff in pre-init phase (read config, create blocks and items, register them)
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		int defaultEnum = 600;
		
		
		wandGeneric = new OrdinaryWand(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandNonmagical", getNextItemID(defaultEnum++)).getInt());
		wandOfMagicMissile = new WandOfMagicMissile(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfMagicMissile", getNextItemID(defaultEnum++)).getInt());
		wandOfFire = new WandOfFire(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfFire", getNextItemID(defaultEnum++)).getInt());
		wandOfDeath = new WandOfDeath(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfDeath", getNextItemID(defaultEnum++)).getInt());
		wandOfGrowth = new WandOfGrowth(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfGrowth", getNextItemID(defaultEnum++)).getInt());
		wandOfHarvesting = new WandOfHarvesting(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfHarvesting", getNextItemID(defaultEnum++)).getInt());
		wandOfHealing = new WandOfHealing(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfHealing", getNextItemID(defaultEnum++)).getInt());
		wandOfIce = new WandOfIce(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfIce", getNextItemID(defaultEnum++)).getInt());
		wandOfMining = new WandOfMining(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfMining", getNextItemID(defaultEnum++)).getInt());
		wandOfTeleportation = new WandOfTeleportation(config.getBlock(Configuration.CATEGORY_ITEM,"itemID_wandOfTeleportation", getNextItemID(defaultEnum++)).getInt());
		 
		
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

		// Register the wand items
		wandGeneric.setUnlocalizedName("wandGeneric");
		LanguageRegistry.addName(wandGeneric, "Nonmagical Wand");

		wandOfMagicMissile.setUnlocalizedName("wandOfMagicMissiles");
		LanguageRegistry.addName(wandOfMagicMissile, "Wand of Magic Missiles");

		wandOfFire.setUnlocalizedName("wandOfFire");
		LanguageRegistry.addName(wandOfFire, "Wand of Fireballs");

		wandOfDeath.setUnlocalizedName("wandOfDeath");
		LanguageRegistry.addName(wandOfDeath, "Wand of Death");

		wandOfGrowth.setUnlocalizedName("wandOfGrowth");
		LanguageRegistry.addName(wandOfGrowth, "Wand of Growth");

		wandOfHarvesting.setUnlocalizedName("wandOfHarvesting");
		LanguageRegistry.addName(wandOfHarvesting, "Wand of Harvesting");

		wandOfHealing.setUnlocalizedName("wandOfHealing");
		LanguageRegistry.addName(wandOfHealing, "Wand of Healing");

		wandOfIce.setUnlocalizedName("wandOfIce");
		LanguageRegistry.addName(wandOfIce, "Wand of Freezing");

		wandOfMining.setUnlocalizedName("wandOfMining");
		LanguageRegistry.addName(wandOfMining, "Wand of Mining");

		wandOfTeleportation.setUnlocalizedName("wandOfTeleportation");
		LanguageRegistry.addName(wandOfTeleportation, "Wand of Ender Step");

		// add crafting recipes (3X3 shaped)
		// Item stack (ID, Count, Meta)
		ItemStack emerald = new ItemStack(Item.emerald);
		ItemStack stick = new ItemStack(Item.stick);
		ItemStack gold = new ItemStack(Item.goldNugget);
		ItemStack x;
		ItemStack output;

		// Nonmagical
		output = new ItemStack(wandGeneric);
		GameRegistry.addRecipe(output, " g ", " s ", " g ", 'g', gold, 's',
				stick);
		// Magic Missile
		output = wandItemStack(wandOfMagicMissile);
		x = new ItemStack(Item.swordGold);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);
		// Fire
		output = wandItemStack(wandOfFire);
		x = new ItemStack(Item.fireballCharge);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

		// Death
		output = wandItemStack(wandOfDeath);
		x = new ItemStack(Item.skull);
		x.setItemDamage(1);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

		// Growth
		output = wandItemStack(wandOfGrowth);
		x = new ItemStack(Item.bone);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

		// Hrvesting
		output = wandItemStack(wandOfHarvesting);
		x = new ItemStack(Item.shears);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

		// Healing
		output = wandItemStack(wandOfHealing);
		x = new ItemStack(Item.ghastTear);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

		// Ice
		output = wandItemStack(wandOfIce);
		x = new ItemStack(Item.snowball);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

		// Digging
		output = wandItemStack(wandOfMining);
		x = new ItemStack(Item.pickaxeGold);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

		// Teleport
		output = wandItemStack(wandOfTeleportation);
		x = new ItemStack(Item.eyeOfEnder);
		GameRegistry.addRecipe(output, "xex", " s ", " g ", 'x', x, 'e',
				emerald, 's', stick, 'g', gold);

	}
    
    private ItemStack wandItemStack(Wand w){
    	ItemStack i = new ItemStack(w);
    	i.setRepairCost(w.getBaseRepairCost());
    	return i;
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
