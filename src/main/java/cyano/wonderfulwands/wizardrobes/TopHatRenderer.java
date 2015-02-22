package cyano.wonderfulwands.wizardrobes;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TopHatRenderer extends ModelBiped  {
	TopHatBlackModel hatBlack;
	TopHatWhiteModel hatBand;
	public ResourceLocation blackTexture = new ResourceLocation(WonderfulWands.MODID+":textures/witchblack.png");
	public ResourceLocation whiteTexture = new ResourceLocation(WonderfulWands.MODID+":textures/fancywhite.png");
	
	public TopHatRenderer(){
		hatBlack = new TopHatBlackModel();
		hatBand = new TopHatWhiteModel();
	}
	
	@SideOnly(Side.CLIENT)
	/** render the hat on head */
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        Tessellator tessellator = Tessellator.getInstance();
		
		float yrot = par5 / (180F / (float)Math.PI);
		float xrot = par6 / (180F / (float)Math.PI);
		
		
		Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(blackTexture);
		hatBlack.render(yrot,xrot);
		Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(whiteTexture);
		hatBand.render(yrot,xrot);
    }
}
