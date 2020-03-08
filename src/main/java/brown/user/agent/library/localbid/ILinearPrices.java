package brown.user.agent.library.localbid;

import java.util.Set;

import brown.platform.item.IItem;

public interface ILinearPrices {
	public ILinearPrices copy();
	public void setPrice(IItem good, double price);
	public double getPrice(IItem good);
	public Set<IItem> goods();
	public boolean contains(IItem good);
	public int size();
	public void remove(IItem good);
}