package brown.accounting.bidbundle.library;

/**
 * A bid bundle can be simple or complex:
 * - a simple bid bundle consists of one price bid per good
 * - a complex bid bundle consists bids for vectors of goods
 * - a lemonade bundle consists of an integer action
 * @author acoggins
 */
public enum BundleType {
	Simple, Complex, Lemonade
}