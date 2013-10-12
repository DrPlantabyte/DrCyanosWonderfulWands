package cyano.wizardrobes;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class TopHatWhiteModel extends ModelBase  {
	final ModelRenderer hat;
	
	public TopHatWhiteModel(){
		float offset = 5f;
		int brimHeight = 1;
		hat = new ModelRenderer(this, 0, 0);
		hat.textureWidth = 16;
		hat.textureHeight = 16;
		hat.addBox(-4.5f, offset+brimHeight, -4.5f, 9, brimHeight, 9, 0.0f);
	}
	
	public void render(float rotationYangle, float rotationXangle){
		hat.rotateAngleY = rotationYangle;
		hat.rotateAngleX = rotationXangle+(float)Math.PI;
		
		
		hat.render(1f/15f);
	}
}
