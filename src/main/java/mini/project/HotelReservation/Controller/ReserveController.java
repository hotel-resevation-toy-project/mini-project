package mini.project.HotelReservation.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReserveController {

    private static ReservationService reservationService;

    @Autowired
    public ReserveController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reserveForm(){

        return "";
    }

    @PostMapping("/reservation")
    public String reserve(ReserveDto reserveDto){

        return "";
    }

    @GetMapping("/reservations")
    public String reservationList(){

        return "";
    }

    @GetMapping("/reservation")
    public String reservationDetail(String reserveNumber){

        return "";
    }

    @DeleteMapping("/cancel")
    public String reserveCancel(String reserveNumber){

        return "";
    }

}
