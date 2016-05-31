package cyano.wonderfulwands;


import cyano.wonderfulwands.blocks.FeyRail;
import cyano.wonderfulwands.blocks.IllusoryBlock;
import cyano.wonderfulwands.blocks.MageLight;
import cyano.wonderfulwands.blocks.PoweredFeyRail;
import cyano.wonderfulwands.entities.EntityLightWisp;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;
import cyano.wonderfulwands.projectiles.EntityWandLightningBolt;
import cyano.wonderfulwands.wands.*;
import cyano.wonderfulwands.wizardrobes.TopHat;
import cyano.wonderfulwands.wizardrobes.WitchsHat;
import cyano.wonderfulwands.wizardrobes.WizardingArmor;
import cyano.wonderfulwands.wizardrobes.WizardsHat;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;

@Mod(modid = WonderfulWands.MODID, name=WonderfulWands.NAME, version = WonderfulWands.VERSION,
		acceptedMinecraftVersions = "[1.9.4,)")
public class WonderfulWands {
    public static final String MODID = "wonderfulwands";
    public static final String NAME ="Cyano's Wonderful Wands";
    public static final String VERSION = "2.1.0";
	
    @SidedProxy(clientSide="cyano.wonderfulwands.ClientProxy", serverSide="cyano.wonderfulwands.ServerProxy")
    public static Proxy proxy;
    
	public static Item wandGeneric = null;
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
	public static Wand wandOfGreaterLight = null;
	public static Wand wandOfStorms = null;
	public static Wand wandOfLightning = null;
	public static Wand wandOfBridging = null;
	public static Wand wandOfClimbing = null;
	public static Wand wandOfIllusions = null;
	public static Wand wandOfRails = null;
	public static Wand wandOfWebbing = null;
	public static Wand wandOfLevitation = null;
	public static Wand wandOfTunneling = null; 

	public static Block mageLight = null;
	public static Block feyRail = null;
	public static Block feyRailPowered = null;
	
	public static boolean altRecipes = false;
	
	public static WizardingArmor[][] robes = new WizardingArmor[16][4]; // [color][slot]

	public static WizardsHat wizardHat = null;
	public static WitchsHat witchHat = null;
	public static TopHat topHat = null;

	private static final String[] colorSuffixes = {"black","red","green","brown","blue","purple","cyan",
		"silver","gray","pink","lime","yellow","light_blue","magenta","orange","white"};
	private static final String[] oreDictionaryColors = {"dyeBlack","dyeRed","dyeGreen","dyeBrown","dyeBlue","dyePurple","dyeCyan",
		"dyeLightGray","dyeGray","dyePink","dyeLime","dyeYellow","dyeLightBlue","dyeMagenta","dyeOrange","dyeWhite"};

	
	public static ItemArmor.ArmorMaterial NONARMOR = null;
	public static ItemArmor.ArmorMaterial WIZARDROBES = null;

	public static MyCreativeTab robesTab;
	public static MyCreativeTab wandsTab;

	private final EntityEquipmentSlot[] armorSlots = {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
	
	// Mark this method for receiving an FMLEvent (in this case, it's the FMLPreInitializationEvent)
    @EventHandler public void preInit(FMLPreInitializationEvent event)
    {
        // Do stuff in pre-init phase (read config, create blocks and items, register them)
    	// load config
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	

 		robesTab = new MyCreativeTab(MODID+".armor");
 		wandsTab = new MyCreativeTab(MODID+".wands");
    	
    	altRecipes = config.getBoolean("alternative_recipes", "options", false, 
				"If true, then robes and wands will use different recipes than normal");
		
    	NONARMOR = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("NONARMOR","empty_armor",10,new int[]{0, 0, 0, 0},0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    	WIZARDROBES = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("WIZARDCLOTH","wizard_robes", 15,new int[]{1, 1, 1, 1},40, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    	
		wandGeneric = new Item().setUnlocalizedName(MODID+"_wand_ordinary").setCreativeTab(wandsTab);
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
		wandOfGreaterLight = new WandOfGreaterLight();
		wandOfStorms = new WandOfStorms();
		wandOfLightning = new WandOfLightning();
		wandOfBridging = new WandOfBridging(Blocks.COBBLESTONE);
		wandOfClimbing = new WandOfClimbing();
		wandOfIllusions = new WandOfIllusions();
		wandOfRails = new WandOfRails();
		wandOfWebbing = new WandOfWebbing();
		wandOfLevitation = new WandOfLevitation();
		wandOfTunneling = new WandOfTunneling();
		
		mageLight = new MageLight();
		registerBlock(mageLight, MageLight.name);
		IllusoryBlock illusion;
		illusion = new IllusoryBlock(Blocks.DIRT);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.GRASS);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.PLANKS.getMapColor(Blocks.PLANKS.getDefaultState()),"illusion_oak_planks",Blocks.PLANKS);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.BOOKSHELF);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.STONE); illusion.setUnlocalizedName("stone.stone");
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.COBBLESTONE.getMapColor(Blocks.COBBLESTONE.getDefaultState()),"illusion_cobblestone", Blocks.COBBLESTONE, "stonebrick");
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.BRICK_BLOCK);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.HAY_BLOCK.getMapColor(Blocks.HAY_BLOCK.getDefaultState()),"illusion_hay_block",Blocks.HAY_BLOCK);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.PUMPKIN);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.MELON_BLOCK);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.NETHER_BRICK.getMapColor(Blocks.NETHER_BRICK.getDefaultState()),"illusion_nether_brick",Blocks.NETHER_BRICK);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.NETHERRACK.getMapColor(Blocks.NETHERRACK.getDefaultState()),"illusion_netherrack",Blocks.NETHERRACK);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.END_STONE.getMapColor(Blocks.END_STONE.getDefaultState()),"illusion_end_stone",Blocks.END_STONE);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.COBBLESTONE.getMapColor(Blocks.PRISMARINE.getDefaultState()),"illusion_prismarine", Blocks.PRISMARINE, "prismarine.rough");
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.SOUL_SAND.getMapColor(Blocks.SOUL_SAND.getDefaultState()),"illusion_soul_sand",Blocks.SOUL_SAND);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.STONEBRICK.getMapColor(Blocks.STONEBRICK.getDefaultState()),"illusion_stonebrick",Blocks.STONEBRICK);
		registerBlock(illusion,illusion.name);
		illusion = new IllusoryBlock(Blocks.SAND);
		registerBlock(illusion,illusion.name);

		feyRail = new FeyRail();
		feyRail.setUnlocalizedName("feyrail");
		feyRail.setCreativeTab(wandsTab);
		registerBlock(feyRail,"feyrail");
		feyRailPowered = new PoweredFeyRail();
		feyRailPowered.setUnlocalizedName("powered_feyrail");
		feyRailPowered.setCreativeTab(wandsTab);
		registerBlock(feyRailPowered,"powered_feyrail");

		

		// Register the wand items
		registerItem(wandGeneric, "wand_ordinary");
		registerItem(wandOfMagicMissile, WandOfMagicMissile.itemName);
		registerItem(wandOfFire, WandOfFire.itemName);
		registerItem(wandOfDeath, WandOfDeath.itemName);
		registerItem(wandOfGrowth, WandOfGrowth.itemName);
		registerItem(wandOfHarvesting, WandOfHarvesting.itemName);
		registerItem(wandOfHealing, WandOfHealing.itemName);
		registerItem(wandOfIce, WandOfIce.itemName);
		registerItem(wandOfMining, WandOfMining.itemName);
		registerItem(wandOfTeleportation, WandOfTeleportation.itemName);
		registerItem(wandOfLight, WandOfLight.itemName);
		registerItem(wandOfGreaterLight, WandOfGreaterLight.itemName);
		registerItem(wandOfStorms, WandOfStorms.itemName);
		registerItem(wandOfLightning, WandOfLightning.itemName);
		registerItem(wandOfBridging, WandOfBridging.itemName);
		registerItem(wandOfClimbing, WandOfClimbing.itemName);
		registerItem(wandOfIllusions, WandOfIllusions.itemName);
		registerItem(wandOfRails, WandOfRails.itemName);
		registerItem(wandOfWebbing, WandOfWebbing.itemName);
		registerItem(wandOfLevitation, WandOfLevitation.itemName);
		registerItem(wandOfTunneling, WandOfTunneling.itemName);
		
		// recipes

		// add crafting recipes (3X3 shaped)
		// Item stack (ID, Count, Meta)
		ItemStack x;
		ItemStack output;

		// Nonmagical
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wandGeneric), " g ", " s ", " g ", 'g', "nuggetGold", 's',"stickWood"));
		// Magic Missile
		addWandRecipe(wandOfMagicMissile,Items.GOLDEN_SWORD);
		// Fire
		addWandRecipe(wandOfFire,Items.FIRE_CHARGE);
		// Death
		addWandRecipe(wandOfDeath,new ItemStack(Items.SKULL,1,1));
		// Growth
		addWandRecipe(wandOfGrowth,Items.BONE);
		// Harvesting
		addWandRecipe(wandOfHarvesting,Items.SHEARS);
		// Healing
		addWandRecipe(wandOfHealing,Items.GHAST_TEAR);
		// Ice
		addWandRecipe(wandOfIce,Items.SNOWBALL);
		// Digging
		addWandRecipe(wandOfMining,Items.GOLDEN_PICKAXE);
		// Teleport
		addWandRecipe(wandOfTeleportation, Items.ENDER_EYE);
		// wand of light
		OreDictionary.registerOre("torch", Blocks.TORCH);
		addWandRecipe(wandOfLight,"torch");
		addWandRecipe(wandOfGreaterLight,new ItemStack(Blocks.REDSTONE_LAMP));
		// wand of storms
		addWandRecipe(wandOfStorms,new ItemStack(Blocks.WOOL,1,7));
		// wand of lightning
		addWandRecipe(wandOfLightning,"gemDiamond");
		// wand of climbing
		OreDictionary.registerOre("vine", Blocks.VINE);
		addWandRecipe(wandOfClimbing,"vine");
		// wand of bridging
		addWandRecipe(wandOfBridging,"blockIron");
		addWandRecipe(wandOfBridging,"blockSteel");
		// wand of illusions
		addWandRecipe(wandOfIllusions,Items.FERMENTED_SPIDER_EYE);
		// wand of railroads
		addWandRecipe(wandOfRails,Blocks.GOLDEN_RAIL);
		// wand of webs
		addWandRecipe(wandOfWebbing,Items.SLIME_BALL);
		// wand of levitation
		addWandRecipe(wandOfLevitation,Items.FEATHER);
		// wand of tunneling
		addWandRecipe(wandOfTunneling,Items.DIAMOND_PICKAXE);
		
		// Wizarding Robes
		int robesRenderIndex = proxy.getArmorRenderIndex(MODID+"_robes");
		for(int colorIndex = 0; colorIndex < 16; colorIndex++){
			int slotIndex = 0;
			for(int i = 0; i < 4; i++){
				EntityEquipmentSlot armorSlot = armorSlots[slotIndex];
				String color = colorSuffixes[colorIndex];
				WizardingArmor r = new WizardingArmor(WIZARDROBES,color,armorSlot);
				registerItem(r, "robes_"+color+"_"+WizardingArmor.slotName.get(armorSlot));
				OreDictionary.registerOre(WizardingArmor.slotName.get(armorSlot)+"WizardRobes", r);
				OreDictionary.registerOre("wizardRobes", r);
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(r ,1),WizardingArmor.slotName.get(armorSlot)+"WizardRobes",oreDictionaryColors[colorIndex]));
				robes[colorIndex][slotIndex] = r;
				slotIndex++;
			}
			ItemStack cloth = new ItemStack(Blocks.WOOL,1,15-colorIndex);
			// metadata for wool: white, orange, magenta, lightblue, yellow lime green, pink, gray, light gray, cyan, purple, blue, brown, green, red, black
			if(altRecipes){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][0],1),"ccc", "cgc",  'c', cloth, 'g', "ingotGold"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][1],1),"cgc", "ccc", "ccc",  'c', cloth, 'g', "ingotGold"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][2],1),"ggg", "c c", "c c",  'c', cloth, 'g', "ingotGold"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][3],1),"c c", "g g",  'c', cloth, 'g', "ingotGold"));
			}else{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][0],1),"ccc", "c c",  'c', cloth));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][1],1),"c c", "ccc", "ccc",  'c', cloth));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][2],1),"ccc", "c c", "c c",  'c', cloth));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(robes[colorIndex][3],1),"c c", "c c",  'c', cloth));
			}
		}
		
		
		


		wizardHat = new WizardsHat();
		registerItem(wizardHat, wizardHat.itemName);
		witchHat = new WitchsHat();
		registerItem(witchHat, witchHat.itemName);
		topHat = new TopHat();
		registerItem(topHat, topHat.itemName);
		
		
		if(config.getBoolean("allow_wizard_hat", "options", true, 
				"If true, then the Wizard's Hat and Witch's Hat items will be craftable (if \n" +
				"false, the hats will not be craftable). These hats are very powerful and you \n" +
				"may want to disable them if you expect there to be troublemakers (aka \n" +
				"\"griefers\")")){
			if(altRecipes){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wizardHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.WOOL,1,11), 'd', Blocks.SKULL));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(witchHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.WOOL,1,15), 'd', Blocks.SKULL));
			}else{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wizardHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.WOOL,1,11), 'd', Items.GHAST_TEAR));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(witchHat,1)," d "," b ", "bbb",  'b', new ItemStack(Blocks.WOOL,1,15), 'd', Items.GHAST_TEAR));
			}
		
		}
		GameRegistry.addRecipe(new ItemStack(topHat,1)," b "," l ",  'b', new ItemStack(Blocks.WOOL,1,15), 'l', Items.LEATHER);
		
	//	OreDictionary.initVanillaEntries()
		config.save();
		proxy.preInit(event);
    }

	private static void addWandRecipe(Wand output, Item specialItem){
		if(altRecipes){
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem, 'e',
					"gemEmerald", 's', wandGeneric));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
					"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
    }

	private static void addWandRecipe(Wand output, Block specialItem){
		if(altRecipes){
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem, 'e',
					"gemEmerald", 's', wandGeneric));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
					"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
	}

    private static void addWandRecipe(Wand output, ItemStack specialItem){
    	if(altRecipes){
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem, 'e',
					"gemEmerald", 's', wandGeneric));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
					"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
    }
    private static void addWandRecipe(Wand output, String specialItem_oreDictionary){
    	if(altRecipes){
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem_oreDictionary, 'e',
					"gemEmerald", 's', wandGeneric));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem_oreDictionary, 'e',
					"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
    }
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// register entities
		registerItemRenders();

 		EntityRegistry.registerModEntity(EntityMagicMissile.class, "magic_missile", 0/*id*/, this, 128/*trackingRange*/, 1/*updateFrequency*/, true/*sendsVelocityUpdates*/);

 		EntityRegistry.registerModEntity(EntityWandLightningBolt.class, "bolt_lightning", 1/*id*/, this, 128/*trackingRange*/, 1/*updateFrequency*/, false/*sendsVelocityUpdates*/);

 		EntityRegistry.registerModEntity(EntityLightWisp.class, "WispLight", 2/*id*/, this, 128/*trackingRange*/, 1/*updateFrequency*/, true/*sendsVelocityUpdates*/);
 		
 		robesTab.setIcon(witchHat);
 		wandsTab.setIcon(wandGeneric);
 		
		proxy.init(event);
		
		
	}
    
    private void registerItemRenders() {
    	// client-side only
    	if(proxy instanceof ServerProxy) return;
    	registerItemRender(wandGeneric,"wand_ordinary");
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
    	registerItemRender(wandOfGreaterLight,WandOfGreaterLight.itemName);
    	registerItemRender(wandOfStorms,WandOfStorms.itemName );
    	registerItemRender( wandOfLightning,WandOfLightning.itemName);
    	registerItemRender( wandOfBridging,WandOfBridging.itemName);
    	registerItemRender( wandOfClimbing,WandOfClimbing.itemName);
		registerItemRender( wandOfIllusions,WandOfIllusions.itemName);
		registerItemRender( wandOfRails,WandOfRails.itemName);
		registerItemRender( wandOfWebbing,WandOfWebbing.itemName);
		registerItemRender( wandOfLevitation,WandOfLevitation.itemName);
		registerItemRender( wandOfTunneling,WandOfTunneling.itemName);

		registerItemRender(net.minecraft.item.Item.getItemFromBlock(mageLight),MageLight.name);
		registerItemRender(net.minecraft.item.Item.getItemFromBlock(feyRail),"feyrail");
		registerItemRender(net.minecraft.item.Item.getItemFromBlock(feyRailPowered),"powered_feyrail");
    	
    	for(int color = 0; color < robes.length; color++){
    		for(int slot = 0; slot < robes[0].length; slot++){
    			registerItemRender(robes[color][slot], "robes_"+colorSuffixes[color]+"_"+WizardingArmor.slotName.get(armorSlots[slot]));
    		}
    	}

    	registerItemRender(wizardHat,wizardHat.itemName);
    	registerItemRender(witchHat,witchHat.itemName);
    	registerItemRender(topHat,topHat.itemName);
    	for(Map.Entry<Block, IllusoryBlock> entry : IllusoryBlock.getLookUpTable().entrySet()){
    		IllusoryBlock b = entry.getValue();
    		Block r = entry.getKey();
        	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(net.minecraft.item.Item.getItemFromBlock(b), 0, 
				new ModelResourceLocation(r.getRegistryName(), "inventory"));
    	}
    	IllusoryBlock pb = IllusoryBlock.getLookUpTable().get(Blocks.PLANKS);
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(net.minecraft.item.Item.getItemFromBlock(pb), 0, 
			new ModelResourceLocation(MODID+":"+pb.name, "inventory"));
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

	private static void registerBlock(Block b, String n){
		GameRegistry.register(b.setRegistryName(MODID,n));
	}
	private static void registerItem(Item i, String n){
		GameRegistry.register(i.setRegistryName(MODID,n));
	}
	
	public static String objectDump(Object o) throws IllegalArgumentException, IllegalAccessException {
		if(o == null ){
			return "null object";
		}
		if(o.getClass() == null){
			return "null class";
		}
		StringBuilder sb = new StringBuilder();
		Class c = o.getClass();
		sb.append(c.getName()).append("\n");
		do{
			Field[] fields = c.getDeclaredFields();
			for(Field f : fields){
				f.setAccessible(true);
				sb.append("\t").append(f.getName()).append("=");
				if(f.getType().isArray()){
					sb.append(arrayDump(f.get(o)));
				}else if(f.get(o) instanceof java.util.Map){
					sb.append(mapDump((java.util.Map)f.get(o)));
				}else {
					sb.append(String.valueOf(f.get(o)));
				}
				sb.append("\n");
			}
			c = c.getSuperclass();
		}while(c != null);
		return sb.toString();
	}
	public static String mapDump(java.util.Map map){
		StringBuilder sb = new StringBuilder();
		for(Object key : map.keySet()){
			sb.append(String.valueOf(key)).append("->");
			if(map.get(key).getClass().isArray()){
				sb.append(arrayDump(map.get(key)));
			} else {
				sb.append(String.valueOf(map.get(key)));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public static String arrayDump(Object array){
		StringBuilder sb = new StringBuilder();
		int size = Array.getLength(array);
		sb.append("[ ");
		boolean addComma = false;
		for(int i = 0; i < size; i++){
			if(addComma)sb.append(", ");
			if(Array.get(array, i).getClass().isArray()){
				sb.append(arrayDump(Array.get(array, i)));
			} else if(Array.get(array, i) instanceof java.util.Map){
				sb.append(mapDump((java.util.Map)Array.get(array, i)));
			}else {
				sb.append(String.valueOf(Array.get(array, i)));
			}
			addComma = true;
		}
		sb.append(" ]");
		return sb.toString();
	}

}
