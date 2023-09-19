package mini.project.HotelReservation.Configure.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    String index(){
        return "/user/in";
    }
}
