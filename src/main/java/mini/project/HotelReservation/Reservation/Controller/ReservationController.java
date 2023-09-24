package mini.project.HotelReservation.Reservation.Controller;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.DiscountPriceDto;
import mini.project.HotelReservation.Reservation.Data.Dto.HotelDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Service.ReservationService;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
        return "reservation/selectHotel";
    }
    @GetMapping("/{hotelName}")
    String selectHotel(@PathVariable("hotelName")String hotelName, Model model,
                       @ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto){
        model.addAttribute("hotelName", hotelName);
        model.addAttribute("reservationRequestDto",reservationRequestDto);
        return "redirect:reservation/selectDate";
    }
    @GetMapping("/{checkInDate}&{checkOutDate}")
    String selectDate(@PathVariable("checkInDate")String cid, @PathVariable("checkOutDate")String cod, Model model,
                      @RequestParam("reservationRequestDto") ReservationRequestDto reservationRequestDto){
        ReservationRequestDto requestDto = new ReservationRequestDto(
                reservationRequestDto.getHotelName(),
                LocalDate.parse(cid),
                LocalDate.parse(cod),
                reservationRequestDto.getRoomType(),
                reservationRequestDto.getOneDayPrice()
        );

        model.addAttribute("reservationRequestDto", requestDto);

        return "redirect:reservation/selectRoom";
    }
    @GetMapping("/{roomType}")
    String selectRoom(@PathVariable("roomType")String roomType,Model model,
                      @RequestParam("reservationRequestDto") ReservationRequestDto reservationRequestDto){
        RoomType checkRoomType;
        switch (roomType){
            case "ROOM_TYPE_A_SINGLE" :
                checkRoomType = RoomType.ROOM_TYPE_A_SINGLE;
                break;

            case "ROOM_TYPE_B_TWIN" :
                checkRoomType = RoomType.ROOM_TYPE_B_TWIN;
                break;

            case "ROOM_TYPE_C_QUEEN" :
                checkRoomType = RoomType.ROOM_TYPE_C_QUEEN;
                break;

            default:
                checkRoomType = RoomType.ROOM_TYPE_D_KING;
                break;
        }

        ReservationRequestDto requestDto = new ReservationRequestDto(
                reservationRequestDto.getHotelName(),
                reservationRequestDto.getCheckInDate(),
                reservationRequestDto.getCheckOutDate(),
                checkRoomType,
                reservationRequestDto.getOneDayPrice()
        );
        model.addAttribute("reservationRequestDto", requestDto);
        return "redirect:reservation/reservationPay";
    }
    @PostMapping("/payment")
    String reservePay(Model model){
//        reservationService.reserve(requestDto);
//        requestDto = null; // 결제가 완료 되면 인스턴스 삭제
//        DiscountPriceDto discountPrice(ReservationRequestDto requestDto);
//        DiscountPriceDto discountPriceDto = reservationService.discountPrice();

        return null;
    }


    String reserveCancel(){
        return null;
    }



}
