package brown.platform.managers;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.messages.IValuationMessage;
import brown.platform.item.ICart;

public interface IValuationManager {

    public void createValuation(Constructor<?> distCons, List<Constructor<?>> generatorCons, List<List<Double>> generatorParams,
        ICart items) throws InstantiationException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException;
    
    public void addAgentValuation(Integer agentID, IGeneralValuation valuation); 
    
    public IGeneralValuation getAgentValuation(Integer agentID); 
    
    public List<IValuationDistribution> getDistribution();

    public void lock();
    
    public Map<Integer, IValuationMessage> constructValuationMessages(); 
    
    public void reset(); 
}

