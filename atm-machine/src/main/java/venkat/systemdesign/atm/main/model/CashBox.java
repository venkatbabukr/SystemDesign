package venkat.systemdesign.atm.main.model;

import venkat.systemdesign.atm.main.exceptions.NoCashException;

public interface CashBox {
    Integer getDenomination();

    Integer getNumAvailableNotes();

    void decrementNumAvailableNotes(int decrementCount) throws IllegalStateException;

    default Integer dispenseNotes(Integer dispenseAmt) throws NoCashException {
        Integer count = dispenseAmt / getDenomination();
        if (count > 0 && count < getNumAvailableNotes()) {
            decrementNumAvailableNotes(count);
        } else {
            throw new NoCashException();
        }
        return count;
    }

}
