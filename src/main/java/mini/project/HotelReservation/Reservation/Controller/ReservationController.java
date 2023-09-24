package mini.project.HotelReservation.Reservation.Controller;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.*;
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
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
        reservationRequestDto.setHotelName(hotelName);
        model.addAttribute("reservationRequestDto",reservationRequestDto);

        return "reservation/selectDate";
    }
    @PostMapping("/date")
    String selectDate(Model model, @ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto){
        System.out.println(reservationRequestDto.getCheckInDate());
        System.out.println(reservationRequestDto.getCheckOutDate());
        List<RoomDto> rooms = reservationService.findByRoomList(reservationRequestDto.getHotelName());
        model.addAttribute("reservationRequestDto",reservationRequestDto);
        model.addAttribute("rooms",rooms);

        return "reservation/selectRoom";
    }
    @GetMapping("/room/{roomType}")
    String selectRoom(@PathVariable("roomType")String roomType,Model model,
                      @ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto){
        reservationRequestDto.setRoomType(RoomType.valueOf(roomType));
        DiscountPriceDto discountPriceDto = reservationService.discountPrice(reservationRequestDto);
        model.addAttribute("discountPriceDto",discountPriceDto);
        model.addAttribute("reservationRequestDto", reservationRequestDto);
        return "reservation/reservationPay";
    }
    @PostMapping("/payment")
    String reservePay(@ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto,
                      @ModelAttribute("discountPriceDto") DiscountPriceDto discountPriceDto,
                      Model model){
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
