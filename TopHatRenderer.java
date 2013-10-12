package cyano.wizardrobes;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class TopHatRenderer extends ModelBiped  {
	TopHatBlackModel hatBlack;
	TopHatWhiteModel hatBand;
	public ResourceLocation blackTexture = new ResourceLocation("wizardrobes:textures/witchblack.png");
	public ResourceLocation whiteTexture = new ResourceLocation("wizardrobes:textures/fancywhite.png");
	
	public TopHatRenderer(){
		hatBlack = new TopHatBlackModel();
		hatBand = new TopHatWhiteModel();
	}
	
	/** render the hat on head */
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
		
		float yrot = par5 / (180F / (float)Math.PI);
		float xrot = par6 / (180F / (float)Math.PI);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(blackTexture);
		hatBlack.render(yrot,xrot);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(whiteTexture);
		hatBand.render(yrot,xrot);
    }
}
