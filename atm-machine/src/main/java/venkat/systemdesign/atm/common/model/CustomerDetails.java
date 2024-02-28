package venkat.systemdesign.atm.common.model;

import lombok.Data;

@Data
public class CustomerDetails {
    private String customerName;
    private AccountDetails accountDetails;
}
