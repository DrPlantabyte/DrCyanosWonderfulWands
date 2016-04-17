package cyano.wonderfulwands.graphics;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.projectiles.EntityWandLightningBolt;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class WandLightningBoltRenderer extends Render<EntityWandLightningBolt>{

	public WandLightningBoltRenderer(RenderManager rm) {
		super(rm);
	}

	private static final ResourceLocation texture = new ResourceLocation(WonderfulWands.MODID+":textures/entity/bolt_lightning.png");
	
	/**
     * Actually renders the given argument. 
     */
    public void doRender(EntityWandLightningBolt entity, double x, double y, double z, float yaw, float partialTick)
    {
		this.bindEntityTexture(entity);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();

		GlStateManager.translate((float)x, (float)y, (float)z);
		GlStateManager.rotate(270F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-1*entity.rotationPitch, 0.0F, 0.0F, 1.0F);


		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();

		GlStateManager.enableRescaleNormal();

		double size = 16D * entity.length;
		float fc = 0.05625F;
		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F); // rotate plane each iteration of loop
		for (int i = 0; i < 4; ++i) {
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F); // rotate plane each iteration of loop
			GL11.glNormal3f(0.0F, 0.0F, fc);
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
			vertexbuffer.pos(0.0D, -2.0D, 0.0D).tex( (double)0, (double)0).endVertex();
			vertexbuffer.pos(size, -2.0D, 0.0D).tex( entity.length, (double)0).endVertex();;
			vertexbuffer.pos(size, 2.0D, 0.0D).tex(  entity.length, (double)1).endVertex();;
			vertexbuffer.pos(0.0D, 2.0D, 0.0D).tex(  (double)0, (double)1).endVertex();;
			tessellator.draw();
		}


		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, yaw, partialTick);

    }
	
	/**
     * Returns the location of an entity's texture. 
     */
    protected ResourceLocation getEntityTexture(EntityWandLightningBolt e)
    {
        return texture;
    }

}
