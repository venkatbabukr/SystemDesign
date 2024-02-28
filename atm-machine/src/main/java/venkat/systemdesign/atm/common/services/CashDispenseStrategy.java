package venkat.systemdesign.atm.common.services;

import java.util.Map;

public interface CashDispenseStrategy {
    
    Map<Integer, Integer> dispenseCash(Integer dispenseAmt);
}
