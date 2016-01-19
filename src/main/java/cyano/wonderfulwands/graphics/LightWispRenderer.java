package cyano.wonderfulwands.graphics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cyano.wonderfulwands.WonderfulWands;

@SideOnly(Side.CLIENT)
public class LightWispRenderer extends Render{
	private static final ResourceLocation texture = new ResourceLocation(WonderfulWands.MODID+":textures/entity/light_wisp.png");

    
    public LightWispRenderer(final RenderManager rm) {
        super(rm);
    }
    
    @Override
    public void doRender(final Entity e, final double x, final double y, final double z, final float f1, final float f2) {
        GlStateManager.pushMatrix();
        this.bindEntityTexture(e);
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
     //   final float scale = this.scale;
     //   GlStateManager.scale(scale / 1.0f, scale / 1.0f, scale / 1.0f);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float minU = 0;
        final float maxU = 1;
        final float minV = 0;
        final float maxV = 1;
        this.bindTexture(this.getEntityTexture(e));
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        worldRenderer.pos(-0.5D, -0.25D, 0.0D).tex(minU, maxV).normal(0.0F, 1.0F, 0.0F).endVertex();
        worldRenderer.pos(0.5D, -0.25D, 0.0D).tex( maxU, maxV).normal(0.0F, 1.0F, 0.0F).endVertex();
        worldRenderer.pos(0.5D, 0.75D, 0.0D).tex(  maxU, minV).normal(0.0F, 1.0F, 0.0F).endVertex();
        worldRenderer.pos(-0.5D, 0.75D, 0.0D).tex( minU, minV).normal(0.0F, 1.0F, 0.0F).endVertex();
        instance.draw();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(e, x, y, z, f1, f2);
    }
    
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity e) {
        return texture;
    }
}
