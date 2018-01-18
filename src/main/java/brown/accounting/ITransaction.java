package brown.accounting;

import brown.accounting.library.Transaction;

public interface ITransaction {
  
  public Transaction sanitize(Integer ID);
  
}