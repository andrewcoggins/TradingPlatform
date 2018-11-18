package brown.user.main;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.mechanism.tradeable.ITradeable;
import java.util.List;
import brown.platform.market.library.SimultaneousMarket;

public class SimulationConfig {

    public final List<ITradeable> allTradeables;
    public final List<ITradeable> endowTradeables;
    public final Integer endowMoney;
    public final List<SimultaneousMarket> marketRules;
    public final IValuationGenerator generator;
    public final IValuationDistribution distribution;
    public final Integer simulationRuns;

    public SimulationConfig(Integer simulationRuns, List<ITradeable> allTradeables, List<ITradeable> endowTradeables, Integer endowmentMoney,
                            List<SimultaneousMarket> marketRules, IValuationGenerator generator, IValuationDistribution distribution) {

        this.allTradeables = allTradeables;
        this.endowTradeables = endowTradeables;
        this.endowMoney = endowmentMoney;
        this.marketRules = marketRules;
        this.generator = generator;
        this.distribution = distribution;
        this.simulationRuns = simulationRuns;
    }
}
