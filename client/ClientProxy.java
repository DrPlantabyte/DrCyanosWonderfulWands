package cyano.wonderfulwands.client;

import cyano.wonderfulwands.CommonProxy;


public class ClientProxy extends CommonProxy {
        
        @Override
        public void registerRenderers() {
               // MinecraftForgeClient.preloadTexture(ITEMS_PNG); // old way
               // MinecraftForgeClient.preloadTexture(BLOCK_PNG);
        }
        
        public static void setCustomRenderers()
        {
        	// register item renderers
        }
}