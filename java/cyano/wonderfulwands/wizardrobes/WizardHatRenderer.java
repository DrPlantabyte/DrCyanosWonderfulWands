package cyano.wonderfulwands.wizardrobes;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class WizardHatRenderer extends ModelBiped /*implements IItemRenderer*/ {

	WizardHatModel hatModel;
	public ResourceLocation hatTexture = new ResourceLocation(WonderfulWands.MODID+":textures/blueNstars.png");
	
	public WizardHatRenderer(){
		hatModel = new WizardHatModel();
	}
	
	/** render the hat on head */
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
		float yrot = par5 / (180F / (float)Math.PI);
		float xrot = par6 / (180F / (float)Math.PI);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(hatTexture);
		hatModel.render(yrot,xrot);
    }
	/*
	 @Override
	    public boolean handleRenderType(ItemStack item, ItemRenderType type)
	    {
	        return true;
	    }
	     
	    @Override
	    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	    {
	        return true;
	    }
	     
	    @Override
	    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	    {
	    	
	        switch(type)
	        {
	            case ENTITY:{
	                renderItem3D(0f, 0f, 0f, 0.5f);
	                return;
	            }
	             
	            case EQUIPPED:{
	                renderItem3D(0f, 1f, 1f, 0.5f);
	                return;
	            }
	                 
	            case INVENTORY:{
	                renderItem3D(0f, 0f, 0f, 0.5f);
	                return;
	            }
	             
	            default:return;
	        }
	    }
	    
	    private void renderItem3D(float x, float y, float z, float scale)
	    {
	    GL11.glPushMatrix();
		
		// disable lighting in inventory render
	    GL11.glDisable(GL11.GL_LIGHTING);
		
	    GL11.glTranslatef((float)x + 0.5F, (float)y + 0.75F * 1.0F, (float)z + 0.5F);

	    GL11.glScalef(1.0F, 1.0F, 1.0F);
		
	    FMLClientHandler.instance().getClient().renderEngine.func_110577_a(hatTexture);
		hatModel.render();
		
		// re-enable lighting
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();
	    }*/
}
