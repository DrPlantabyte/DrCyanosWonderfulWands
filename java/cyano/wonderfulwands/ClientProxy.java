package cyano.wonderfulwands;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.*;
import cyano.wonderfulwands.graphics.*;
import cyano.wonderfulwands.projectiles.*;
import cyano.wonderfulwands.wizardrobes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;

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
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        // add renderers
 		RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, new MagicMissileRenderer(rm));
 		RenderingRegistry.registerEntityRenderingHandler(EntityWandLightningBolt.class, new WandLightningBoltRenderer(rm));
 		
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
    
}
