package pl.edu.agh.megamud.base;

/**
 * A simple item, that can be held by anyone and can be dropped everywhere. It plainly does nothing.
 * @author Tomasz
 */
public class SimpleItem extends Item{
	public SimpleItem(String a,String b){
		super(a,b);
	}
	protected boolean canBeGivenTo(ItemHolder owner){
		return true;
	}
}
