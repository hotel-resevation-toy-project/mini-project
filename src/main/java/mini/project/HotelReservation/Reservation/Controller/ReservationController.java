package mini.project.HotelReservation.Reservation.Controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private ReservationRequestDto requestDto = new ReservationRequestDto();
//
//    @GetMapping("/hotels")
//    String hotelList(Model model){
//
//    }
//    @GetMapping("/{hotelName}")
//    String selecteHotel(@PathVariable("hotelName")String hotelName, Model model){
//
//    }
//
//    @GetMapping("/{checkInDate}&{checkOutDate}")
//    String selecteDate(@PathVariable("checkInDate")String cid, @PathVariable("checkOutDate")String cod, Model model){
//
//    }
//
//    @GetMapping("/{roomType}")
//    String seleteRoom(@PathVariable("roomType")String roomType,Model model){
//
//    }
//
//    @PostMapping("/payment")
//    String reservePay(Model model){
//
////        reservationService.reserve(requestDto);
//        requestDto = null; // 결제가 완료 되면 인스턴스 삭제
//    }
//    String reserveCancel(){
//
//    }
}
