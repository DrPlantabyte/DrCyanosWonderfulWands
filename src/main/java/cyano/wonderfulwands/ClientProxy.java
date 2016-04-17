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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
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
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        // client-only init code
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        // add renderers
 		RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class,  new IRenderFactory<EntityMagicMissile> (){
			@Override
			public Render<? super EntityMagicMissile> createRenderFor(RenderManager rm) {return new MagicMissileRenderer(rm);}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWandLightningBolt.class,  new IRenderFactory<EntityWandLightningBolt> (){
			@Override
			public Render<? super EntityWandLightningBolt> createRenderFor(RenderManager rm) {return new WandLightningBoltRenderer(rm);}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityLightWisp.class,  new IRenderFactory<EntityLightWisp> (){
			@Override
			public Render<? super EntityLightWisp> createRenderFor(RenderManager rm) {return new LightWispRenderer(rm);}
		});
 		
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
