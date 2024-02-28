package venkat.systemdesign.atm.admin;

import org.junit.jupiter.api.Test;

import venkat.systemdesign.atm.admin.controller.AdminUIController;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTab9GenTest {

    @Test
    void index() {
        AdminUIController adminController = new AdminUIController();
        String result = adminController.index();
        assertEquals("admin", result);
    }
}
