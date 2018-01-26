package brown.bidbundle;

/**
 * A bid bundle can be simple or complex:
 * - an auction bid bundle consists of one price bid per ITradeable
 * - a game bundle consists of an integer action
 * @author acoggins
 */
public enum BundleType {
	AUCTION, GAME
}