package cyano.wonderfulwands.wands;

import net.minecraft.creativetab.CreativeTabs;
import cyano.wonderfulwands.WonderfulWands;

public class OrdinaryWand extends Wand{

	public static final String itemName = "wand_ordinary";
	public OrdinaryWand() {
		super();
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public int getUseCost() {
		return 0;
	}
	
	@Override public int getBaseRepairCost(){
		return 0;
	}

}
