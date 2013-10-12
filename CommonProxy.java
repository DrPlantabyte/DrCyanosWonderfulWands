package cyano.wizardrobes;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class CommonProxy {

	public void registerRenderers() {
		// Do nothing (server doesn't render graphics)
	}
	
	public int getArmorRenderIndex(String armorSet){
		return 0; // Server don't care!
	}
}

