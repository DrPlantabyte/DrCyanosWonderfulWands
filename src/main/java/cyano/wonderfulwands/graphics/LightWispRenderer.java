package cyano.wonderfulwands.graphics;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cyano.wonderfulwands.WonderfulWands;

@SideOnly(Side.CLIENT)
public class LightWispRenderer extends Render{
	private static final ResourceLocation texture = new ResourceLocation(WonderfulWands.MODID+":textures/entity/light_wisp.png");

    protected final Item item;
    private final RenderItem itemRender;
    
    public LightWispRenderer(final RenderManager rm, final Item item, final RenderItem itemRender) {
        super(rm);
        this.item = item;
        this.itemRender = itemRender;
    }
    
    public void doRender(final Entity e, final double x, final double y, final double z, final float f1, final float f2) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        this.bindEntityTexture(e);
        final int textureByXP = e.getTextureByXP();
        final float n = (textureByXP % 4 * 16 + 0) / 64.0f;
        final float n2 = (textureByXP % 4 * 16 + 16) / 64.0f;
        final float n3 = (textureByXP / 4 * 16 + 0) / 64.0f;
        final float n4 = (textureByXP / 4 * 16 + 16) / 64.0f;
        final float n5 = 1.0f;
        final float n6 = 0.5f;
        final float n7 = 0.25f;
        final int brightnessForRender = e.getBrightnessForRender(f2);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n8 = 255.0f;
        final float n9 = (e.xpColor + f2) / 2.0f;
        final int p_setColorRGBA_I_1_ = (int)((MathHelper.sin(n9 + 0.0f) + 1.0f) * 0.5f * n8) << 16 | (int)n8 << 8 | (int)((MathHelper.sin(n9 + 4.1887903f) + 1.0f) * 0.1f * n8);
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        final float n10 = 0.3f;
        GlStateManager.scale(n10, n10, n10);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setColorRGBA_I(p_setColorRGBA_I_1_, 128);
        worldRenderer.setNormal(0.0f, 1.0f, 0.0f);
        worldRenderer.addVertexWithUV(0.0f - n6, 0.0f - n7, 0.0, n, n4);
        worldRenderer.addVertexWithUV(n5 - n6, 0.0f - n7, 0.0, n2, n4);
        worldRenderer.addVertexWithUV(n5 - n6, 1.0f - n7, 0.0, n2, n3);
        worldRenderer.addVertexWithUV(0.0f - n6, 1.0f - n7, 0.0, n, n3);
        instance.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(e, x, y, z, f1, f2);
    }
    
    public ItemStack getItemStack(final Entity e) {
        return new ItemStack(this.item, 1, 0);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity e) {
        return texture;
    }
}
