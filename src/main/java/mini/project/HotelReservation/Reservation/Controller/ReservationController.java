//package mini.project.HotelReservation.Reservation.Controller;
//
//import lombok.RequiredArgsConstructor;
//import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import mini.project.HotelReservation.Reservation.Service.ReservationService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Controller
//@RequiredArgsConstructor
//public class ReservationController {
//
//    private static ReservationService reservationService;
//
//    @Autowired
//    public ReservationController(ReservationService reservationService){
//        this.reservationService = reservationService;
//    }
//
//    @GetMapping("/reservation")
//    public String reserveForm(){
//
//        return "";
//    }
//
//    @PostMapping("/reservation")
//    public String reserve(ReserveDto reserveDto){
//
//        return "";
//    }
//
//    @GetMapping("/reservations")
//    public String reservationList(){
//
//        return "";
//    }
//
//    @GetMapping("/reservation")
//    public String reservationDetail(String reserveNumber){
//
//        return "";
//    }
//
//    @DeleteMapping("/cancel")
//    public String reserveCancel(String reserveNumber){
//
//        return "";
//    }
//
//}
