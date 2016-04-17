package cyano.wonderfulwands;

import cyano.wonderfulwands.entities.EntityLightWisp;
import cyano.wonderfulwands.graphics.LightWispRenderer;
import cyano.wonderfulwands.graphics.MagicMissileRenderer;
import cyano.wonderfulwands.graphics.WandLightningBoltRenderer;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;
import cyano.wonderfulwands.projectiles.EntityWandLightningBolt;
import cyano.wonderfulwands.wizardrobes.TopHatRenderer;
import cyano.wonderfulwands.wizardrobes.WitchHatRenderer;
import cyano.wonderfulwands.wizardrobes.WizardHatRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends Proxy{
	public static TopHatRenderer topHatRenderer;
	public static WizardHatRenderer wizardHatRenderer;
	public static WizardHatRenderer witchHatRenderer;
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        // client-only pre-init code
		// add entity renderers
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, (RenderManager rm)->{return new MagicMissileRenderer(rm);});
		RenderingRegistry.registerEntityRenderingHandler(EntityWandLightningBolt.class, (RenderManager rm)->{return new WandLightningBoltRenderer(rm);});
		RenderingRegistry.registerEntityRenderingHandler(EntityLightWisp.class, (RenderManager rm)->{return new LightWispRenderer(rm);});
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        // client-only init code

		// hat renderers
 		topHatRenderer = new TopHatRenderer();
    	wizardHatRenderer = new WizardHatRenderer();
    	witchHatRenderer = new WitchHatRenderer();

		//
    	
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        // client-only post-init code
    }
    
}
