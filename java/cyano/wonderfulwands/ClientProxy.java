package cyano.wonderfulwands;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cyano.wonderfulwands.graphics.MagicMissileRenderer;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;

public class ClientProxy extends Proxy{

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
     		
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        // client-only post-init code
    }
}
