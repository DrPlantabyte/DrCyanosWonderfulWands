package cyano.wonderfulwands.wands;

public class OrdinaryWand extends Wand{

	public OrdinaryWand(int itemID) {
		super(itemID);
		this.setTextureName("wonderfulwands:wandIconOrdinary");
	}

	@Override
	public int getUseCost() {
		return 0;
	}
	
	@Override public int getBaseRepairCost(){
		return 0;
	}

}
