package cyano.wonderfulwands;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cyano.wonderfulwands.graphics.*;
import cyano.wonderfulwands.projectiles.*;
import cyano.wonderfulwands.wizardrobes.*;

public class ClientProxy extends Proxy{
	public static TopHatRenderer topHatRenderer;
	public static WizardHatRenderer wizardHatRenderer;
	public static WizardHatRenderer witchHatRenderer;
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        // client-only pre-init code
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        // client-only init code
        // add renderers
 		RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, new MagicMissileRenderer());
 		RenderingRegistry.registerEntityRenderingHandler(EntityWandLightningBolt.class, new WandLightningBoltRenderer());
    	topHatRenderer = new TopHatRenderer();
    	wizardHatRenderer = new WizardHatRenderer();
    	witchHatRenderer = new WizardHatRenderer();
    	witchHatRenderer.hatTexture = new ResourceLocation(WonderfulWands.MODID+":textures/witchblack.png");
     		
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        // client-only post-init code
    }
    
    @Override public int getArmorRenderIndex(String armorSet){
		return RenderingRegistry.addNewArmourRendererPrefix(armorSet);
	}
}
