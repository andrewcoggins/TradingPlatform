package brown.user.agent.library.localbid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.platform.item.IItem;

public interface IBidVector {
	public IBidVector copy();
	public void setBid(IItem good, double bid);
	public double getBid(IItem good);
	public Set<IItem> goods();
	public boolean contains(IItem good);
}