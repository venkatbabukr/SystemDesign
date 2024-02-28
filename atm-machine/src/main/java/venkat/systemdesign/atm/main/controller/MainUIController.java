package venkat.systemdesign.atm.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainUIController {
    
    @GetMapping("/main")
    public String index() {
        return "main";
    }

}
