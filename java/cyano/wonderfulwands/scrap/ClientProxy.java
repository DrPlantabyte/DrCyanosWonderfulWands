package cyano.wonderfulwands.scrap;



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