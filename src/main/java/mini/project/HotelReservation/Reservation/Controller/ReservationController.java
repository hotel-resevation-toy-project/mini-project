package mini.project.HotelReservation.Reservation.Controller;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.DiscountPriceDto;
import mini.project.HotelReservation.Reservation.Data.Dto.HotelDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.Reservation.Service.ReservationService;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/main")
    String mainPage(Model model){
        return "reservation/main";
    }

    @GetMapping("/hotels")
    String hotelList(Model model){
        model.addAttribute("hotelListDto",reservationService.findByHotelList());
        return "reservation/selectHotel";
    }

    @GetMapping("/hotel/{hotelName}")
    String selectHotel(@PathVariable("hotelName")String hotelName, Model model){
        System.out.println("hotelName = " + hotelName);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
        reservationRequestDto.setHotelName(hotelName);
        model.addAttribute("reservationRequestDto",reservationRequestDto);
        return "reservation/selectDate";
    }
    @GetMapping("/date/{checkInDate}&{checkOutDate}")
    String selectDate(@PathVariable("checkInDate") LocalDate cid, @PathVariable("checkOutDate")LocalDate cod, Model model,
                      @ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto){
        reservationRequestDto.setCheckInDate(cid);
        reservationRequestDto.setCheckOutDate(cod);
        model.addAttribute("reservationRequestDto",reservationRequestDto);
        return "reservation/selectRoom";
    }
    @GetMapping("/room/{roomType}")
    String selectRoom(@PathVariable("roomType")String roomType,Model model,
                      @ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto){
        reservationRequestDto.setRoomType(RoomType.valueOf(roomType));
        model.addAttribute("reservationRequestDto", reservationRequestDto);
        return "reservation/reservationPay";
    }
    @PostMapping("/payment")
    String reservePay(@ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto, Model model){
        DiscountPriceDto discountPriceDto = reservationService.discountPrice(reservationRequestDto);
        ReservationResponseDto reservationResponseDto = reservationService.reserve(reservationRequestDto, discountPriceDto);
        model.addAttribute("reservationResponseDto",reservationResponseDto);
        model.addAttribute("discountPriceDto",discountPriceDto);
        return "user/reservationInfo";
    }

    @GetMapping()
    String reserveCancel(){
        return null;
    }
}
