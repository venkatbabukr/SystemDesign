package venkat.systemdesign.atm.common.model;

import java.util.TreeSet;

import lombok.Data;

@Data
public class CashRack {
    private TreeSet<CashBox> cashBoxes;
}
