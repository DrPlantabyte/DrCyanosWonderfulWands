package cyano.wonderfulwands;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.reflect.ConstructorAccessor;
import sun.reflect.ReflectionFactory;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import cyano.wonderfulwands.blocks.MageLight;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;
import cyano.wonderfulwands.projectiles.EntityWandLightningBolt;
import cyano.wonderfulwands.wands.OrdinaryWand;
import cyano.wonderfulwands.wands.Wand;
import cyano.wonderfulwands.wands.WandOfDeath;
import cyano.wonderfulwands.wands.WandOfFire;
import cyano.wonderfulwands.wands.WandOfGrowth;
import cyano.wonderfulwands.wands.WandOfHarvesting;
import cyano.wonderfulwands.wands.WandOfHealing;
import cyano.wonderfulwands.wands.WandOfIce;
import cyano.wonderfulwands.wands.WandOfLight;
import cyano.wonderfulwands.wands.WandOfLightning;
import cyano.wonderfulwands.wands.WandOfMagicMissile;
import cyano.wonderfulwands.wands.WandOfMining;
import cyano.wonderfulwands.wands.WandOfStorms;
import cyano.wonderfulwands.wands.WandOfTeleportation;
import cyano.wonderfulwands.wizardrobes.TopHat;
import cyano.wonderfulwands.wizardrobes.WitchsHat;
import cyano.wonderfulwands.wizardrobes.WizardingArmor;
import cyano.wonderfulwands.wizardrobes.WizardsHat;

@Mod(modid = WonderfulWands.MODID, name=WonderfulWands.NAME, version = WonderfulWands.VERSION)
public class WonderfulWands {
    public static final String MODID = "wonderfulwands";
    public static final String NAME ="Cyano's Wonderful Wands";
    public static final String VERSION = "1.5.0";
	
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
	
	public static WizardingArmor[][] robes = new WizardingArmor[16][4]; // [color][slot]

	public static WizardsHat wizardHat = null;
	public static WitchsHat witchHat = null;
	public static TopHat topHat = null;

	private static final String[] colorSuffixes = {"black","red","green","brown","blue","purple","cyan",
		"silver","gray","pink","lime","yellow","light_blue","magenta","orange","white"};
	private static final String[] oreDictionaryColors = {"dyeBlack","dyeRed","dyeGreen","dyeBrown","dyeBlue","dyePurple","dyeCyan",
		"dyeLightGray","dyeGray","dyePink","dyeLime","dyeYellow","dyeLightBlue","dyeMagenta","dyeOrange","dyeWhite"};

	
	public static ItemArmor.ArmorMaterial NONARMOR = null;
	public static ItemArmor.ArmorMaterial WIZARDROBE = null;
	
	// Mark this method for receiving an FMLEvent (in this case, it's the FMLPreInitializationEvent)
    @EventHandler public void preInit(FMLPreInitializationEvent event)
    {
        // Do stuff in pre-init phase (read config, create blocks and items, register them)
    	// load config
    	// TODO: Forge Update:	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	// TODO: Forge Update:	config.load();
		

    	NONARMOR = newArmorMaterial("NONARMOR",10,new int[]{0, 0, 0, 0},0);
    	WIZARDROBE = newArmorMaterial("WIZARDCLOTH", 15,new int[]{1, 1, 1, 1},40);
    	
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
		wandOfLightning = new WandOfLightning();
		
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
		GameRegistry.registerItem(wandOfLightning, WandOfLightning.itemName);
		
		// recipes

		// add crafting recipes (3X3 shaped)
		// Item stack (ID, Count, Meta)
		ItemStack x;
		ItemStack output;

		// Nonmagical
		// TODO: Forge Update:	GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(wandGeneric), " g ", " s ", " g ", 'g', "nuggetGold", 's',"stickWood"));
		GameRegistry.addShapedRecipe(wandItemStack(wandGeneric), " g ", " s ", " g ", 'g', Items.gold_nugget, 's',Items.stick);
		// Magic Missile
		addWandRecipe(wandOfMagicMissile,Items.golden_sword);
		// Fire
		addWandRecipe(wandOfFire,Items.fire_charge);
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
		// TODO: Forge Update:	addWandRecipe(wandOfLight,"dustGlowstone");
		addWandRecipe(wandOfLight,Items.glowstone_dust);
		// wand of storms
		addWandRecipe(wandOfStorms,new ItemStack(Blocks.wool,1,7));
		// wand of lightning
		// TODO: Forge Update:	addWandRecipe(wandOfLightning,"gemDiamond");
		addWandRecipe(wandOfLightning,Items.diamond);
		
		// TODO: make armor render
		if(true){
			proxy.preInit(event);
			return;
		}
		// Wizarding Robes
		int robesRenderIndex = proxy.getArmorRenderIndex(MODID+"_robes");
		for(int colorIndex = 0; colorIndex < 16; colorIndex++){
			for(int armorSlot = 0; armorSlot < 4; armorSlot++){
				String color = colorSuffixes[colorIndex];
				WizardingArmor r = new WizardingArmor(color,armorSlot);
				GameRegistry.registerItem(r, "robes_"+color+"_"+WizardingArmor.slotName[armorSlot]);
				// TODO: Forge Update:	OreDictionary.registerOre(WizardingArmor.slotName[armorSlot]+"WizardRobes", r);
				// TODO: Forge Update:	OreDictionary.registerOre("wizardRobes", r);
				// TODO: Forge Update:	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(r ,1),WizardingArmor.slotName[armorSlot]+"WizardRobes",oreDictionaryColors[colorIndex]));
				robes[colorIndex][armorSlot] = r;
			}
		}
		// TODO: Forge Update:	get rid of this awefule method of filling recipes
		for(int armorSlot = 0; armorSlot < 4; armorSlot++){
			for(int cin = 0; cin < 16; cin++){
				for(int cout = 0; cin < 16; cin++){
					GameRegistry.addShapelessRecipe(new ItemStack(robes[cout][armorSlot] ,1),robes[cin][armorSlot],new ItemStack(Items.dye,1,cout));
				}
			}
		}
		
		
		ItemStack cloth = new ItemStack(Blocks.wool,1,10);		
		// TODO: Forge Update:	
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[5][0],1),"ccc", "cgc",  'c', cloth, 'g', "ingotGold"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[5][1],1),"cgc", "ccc", "ccc",  'c', cloth, 'g', "ingotGold"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[5][2],1),"ggg", "c c", "c c",  'c', cloth, 'g', "ingotGold"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[5][3],1),"c c", "g g",  'c', cloth, 'g', "ingotGold"));
		GameRegistry.addShapedRecipe(new ItemStack(robes[5][0],1),"ccc", "cgc",  'c', cloth, 'g', Items.gold_ingot);
		GameRegistry.addShapedRecipe(new ItemStack(robes[5][1],1),"cgc", "ccc", "ccc",  'c', cloth, 'g', Items.gold_ingot);
		GameRegistry.addShapedRecipe(new ItemStack(robes[5][2],1),"ggg", "c c", "c c",  'c', cloth, 'g', Items.gold_ingot);
		GameRegistry.addShapedRecipe(new ItemStack(robes[5][3],1),"c c", "g g",  'c', cloth, 'g', Items.gold_ingot);


		wizardHat = new WizardsHat();
		GameRegistry.registerItem(wizardHat, wizardHat.itemName);
		witchHat = new WitchsHat();
		GameRegistry.registerItem(witchHat, witchHat.itemName);
		topHat = new TopHat();
		GameRegistry.registerItem(topHat, topHat.itemName);
		
		// TODO: Forge Update:	
//		if(config.getBoolean("allow_wizard_hat", "options", true, 
//				"If true, then the Wizard's Hat and Witch's Hat items will be craftable (if \n" +
//				"false, the hats will not be craftable). These hats are very powerful and you \n" +
//				"may want to disable them if you expect there to be troublemakers (aka \n" +
//				"\"griefers\")")){
//			
//			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wizardHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.wool,1,11), 'd', "gemDiamond"));
//			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(witchHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.wool,1,15), 'd', "gemDiamond"));
		GameRegistry.addShapedRecipe(new ItemStack(wizardHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.wool,1,11), 'd', Items.diamond);
		GameRegistry.addShapedRecipe(new ItemStack(witchHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.wool,1,15), 'd', Items.diamond);
//		}
		GameRegistry.addRecipe(new ItemStack(topHat,1)," b "," l ",  'b', new ItemStack(Blocks.wool,1,15), 'l', Items.leather);
		
	//	OreDictionary.initVanillaEntries()
		// TODO: Forge Update:	config.save();
		proxy.preInit(event);
    }

    private static void addWandRecipe(Wand output, Item specialItem){
    	// TODO: Forge Update:	GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
		//		"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
    	GameRegistry.addShapedRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
    			Items.emerald, 's', Items.stick, 'g', Items.gold_nugget);
    }

    private static void addWandRecipe(Wand output, ItemStack specialItem){
    	// TODO: Forge Update:	GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
		//		"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
    	GameRegistry.addShapedRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
    			Items.emerald, 's', Items.stick, 'g', Items.gold_nugget);
    }
 // TODO: Forge Update:	
//    private static void addWandRecipe(Wand output, String specialItem_oreDictionary){
//    	GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem_oreDictionary, 'e',
//				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
//    }
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// register entities
		registerItemRenders();
		
		EntityRegistry.registerGlobalEntityID(EntityMagicMissile.class, "magic_missile", EntityRegistry.findGlobalUniqueEntityId());
 		EntityRegistry.registerModEntity(EntityMagicMissile.class, "magic_missile", 0/*id*/, this, 128/*trackingRange*/, 1/*updateFrequency*/, true/*sendsVelocityUpdates*/);
 		
 		EntityRegistry.registerGlobalEntityID(EntityWandLightningBolt.class, "bolt_lightning", EntityRegistry.findGlobalUniqueEntityId());
 		EntityRegistry.registerModEntity(EntityWandLightningBolt.class, "bolt_lightning", 1/*id*/, this, 128/*trackingRange*/, 1/*updateFrequency*/, false/*sendsVelocityUpdates*/);
 		
		proxy.init(event);
		
		
	}
    
    private void registerItemRenders() {
    	registerItemRender(wandGeneric,OrdinaryWand.itemName);
    	registerItemRender(wandOfMagicMissile,WandOfMagicMissile.itemName );
    	registerItemRender(wandOfDeath,WandOfDeath.itemName );
    	registerItemRender(wandOfFire,WandOfFire.itemName );
    	registerItemRender(wandOfGrowth,WandOfGrowth.itemName );
    	registerItemRender(wandOfHarvesting,WandOfHarvesting.itemName );
    	registerItemRender(wandOfHealing,WandOfHealing.itemName );
    	registerItemRender(wandOfIce,WandOfIce.itemName );
    	registerItemRender(wandOfMining,WandOfMining.itemName);
    	registerItemRender( wandOfTeleportation,WandOfTeleportation.itemName);
    	registerItemRender(wandOfLight,WandOfLight.itemName);
    	registerItemRender(wandOfStorms,WandOfStorms.itemName );
    	registerItemRender( wandOfLightning,WandOfLightning.itemName);
    	
    	registerItemRender(net.minecraft.item.Item.getItemFromBlock(mageLight),MageLight.name);
    	
    	// TODO: make armor work
    	if(true) {return;}
    	for(int color = 0; color < robes.length; color++){
    		for(int slot = 0; slot < robes[0].length; slot++){
    			registerItemRender(robes[color][slot], "robes_"+colorSuffixes[color]+"_"+WizardingArmor.slotName[slot]);
    		}
    	}

    	registerItemRender(wizardHat,wizardHat.itemName);
    	registerItemRender(witchHat,witchHat.itemName);
    	registerItemRender(topHat,topHat.itemName);
	}
    
    private void registerItemRender(Item i, String itemName){
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(MODID+":"+itemName, "inventory"));
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
	/** black magic voodoo contained here-in. You have been warned. */ 
	public static ItemArmor.ArmorMaterial newArmorMaterial(String name, int maxDamageFactor, int[] damageReduction,int enchantibility){
		
		try{
			// Avert your eyes! This unholy reflective abomination will harm your soul! 
			Field enumArrayField = ItemArmor.ArmorMaterial.class.getDeclaredField("$VALUES");
			enumArrayField.setAccessible(true);
			Field enumEnumField = Modifier.class.getDeclaredField("ENUM");
			enumEnumField.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(enumArrayField, enumArrayField.getModifiers() & ~Modifier.FINAL);
		    ItemArmor.ArmorMaterial[] oldEnumArray = (ItemArmor.ArmorMaterial[])enumArrayField.get(null);
		   Constructor c = ItemArmor.ArmorMaterial.class.getDeclaredConstructor(
		   		String.class, int.class, String.class, int.class, int[].class,int.class);
			c.setAccessible(true);
		//	ItemArmor.ArmorMaterial newMaterial = (ItemArmor.ArmorMaterial)c.newInstance(name.toUpperCase(),oldEnumArray.length,name.toLowerCase(), maxDamageFactor, damageReduction, enchantibility);
			ItemArmor.ArmorMaterial newMaterial = (ItemArmor.ArmorMaterial) ReflectionFactory.getReflectionFactory()
					.newConstructorAccessor(c)
					.newInstance(new Object[]{name.toUpperCase(),oldEnumArray.length,name.toLowerCase(), maxDamageFactor, damageReduction, enchantibility});
		    ItemArmor.ArmorMaterial[] newEnumArray = new ItemArmor.ArmorMaterial[oldEnumArray.length+1];
		    System.arraycopy(oldEnumArray, 0, newEnumArray, 0, oldEnumArray.length);
		    newEnumArray[oldEnumArray.length] = newMaterial;
		    enumArrayField.set(null, newEnumArray);
			
			return newMaterial;
		}catch(Exception ex){
			Logger.getLogger(MODID).log(Level.SEVERE, "Failed to add armor material enum '"+name+"'",ex);
			ex.printStackTrace(System.err);
			return null;
		}
		
	}
}
