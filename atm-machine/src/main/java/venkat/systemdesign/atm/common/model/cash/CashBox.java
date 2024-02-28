package venkat.systemdesign.atm.common.model;

import lombok.Data;

@Data
public class CashBox implements Comparable<CashBox> {
    private int denomination;
    private int numNotes;
    @Override
    public int compareTo(CashBox o) {
        return this.denomination - o.denomination;
    }
}
