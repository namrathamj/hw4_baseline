package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ExpenseTrackerModel class represents the model component in the ExpenseTracker application.
 * It manages the list of transactions and provides methods for interacting with the data.
 * This class also serves as an Observable, notifying registered listeners when the state changes.
 */

public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> listeners;
  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    listeners = new ArrayList<>();
  }

  
  /**
     * Adds a new transaction to the model.
     *
     * @param t The transaction to be added.
     * @throws IllegalArgumentException if the provided transaction is null.
     */

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }


  /**
     * Removes a transaction from the model.
     *
     * @param t The transaction to be removed.
     */

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }

  /**
     * Retrieves an unmodifiable list of all transactions in the model.
     *
     * @return An unmodifiable list of transactions.
     */

  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  /**
     * Sets the list of indices representing matched filters in the model.
     *
     * @param newMatchedFilterIndices The list of indices representing matched filters.
     * @throws IllegalArgumentException if the provided list is null or contains invalid indices.
     */

  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
  }

  /**
     * Retrieves a copy of the list of indices representing matched filters in the model.
     *
     * @return A copy of the list of indices representing matched filters.
     */

  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
            return true;
      }
      return false;
  }


  /**
     * Retrieves the number of registered listeners.
     *
     * @return The number of registered listeners.
     */

  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      return listeners.size();
      //return 0;
  }

  /**
     * Checks if the given listener is already registered.
     *
     * @param listener The ExpenseTrackerModelListener to check.
     * @return True if the listener is already registered, false otherwise.
     */

  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      //TODO
      return listeners.contains(listener);
     // return false;
  }

  /**
     * Notifies all registered observers about a change in the model's state.
     */

  public void notifyObservers() {
        stateChanged();
    }

  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      //TODO
      for (ExpenseTrackerModelListener listener : listeners) {
            listener.update(this);
        }
  }
}
