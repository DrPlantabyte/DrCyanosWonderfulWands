package cyano.wonderfulwands.wizardrobes;

import net.minecraft.util.ResourceLocation;
import cyano.wonderfulwands.WonderfulWands;

public class WitchHatRenderer extends WizardHatRenderer {
	public WitchHatRenderer(){
		super();
		super.hatTexture = new ResourceLocation(WonderfulWands.MODID+":textures/witchblack.png");
	}
}
