package cyano.wizardrobes.client;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cyano.wizardrobes.*;


public class ClientProxy extends CommonProxy {
        
		public static TopHatRenderer topHatRenderer;
		public static WizardHatRenderer wizardHatRenderer;
		public static WizardHatRenderer witchHatRenderer;
	
        @Override
        public void registerRenderers() {
        	topHatRenderer = new TopHatRenderer();
        	wizardHatRenderer = new WizardHatRenderer();
        	witchHatRenderer = new WizardHatRenderer();
        	witchHatRenderer.hatTexture = new ResourceLocation("wizardrobes:textures/witchblack.png");
        }
        
        public static void setCustomRenderers()
        {
        	// register item renderers
        }
        
        @Override public int getArmorRenderIndex(String armorSet){
    		return RenderingRegistry.addNewArmourRendererPrefix(armorSet);
    	}
}