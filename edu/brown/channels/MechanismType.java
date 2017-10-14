package brown.channels;

/**
 * an auction is either OpenOutcry, SealedBid, LMSR, 
 * CDA or Lemonade
 * @author acoggins
 *
 */
public enum MechanismType {
	OpenOutcry,
	SealedBid,
	LMSR,
	ContinuousDoubleAuction, Lemonade
}
