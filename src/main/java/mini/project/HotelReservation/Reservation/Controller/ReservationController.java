package mini.project.HotelReservation.Reservation.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.DateDto;
import mini.project.HotelReservation.Reservation.Data.Dto.DiscountPriceDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.RoomDto;
import mini.project.HotelReservation.Reservation.Service.ReservationService;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/main")
    String mainPage(){
        return "reservation/main";
    }

    @GetMapping("/hotels")
    String hotelList(Model model){
        model.addAttribute("hotelListDto",reservationService.findByHotelList());
        return "reservation/selectHotel";
    }

    @GetMapping("/hotel/{hotelName}")
    String selectHotel(@PathVariable("hotelName")String hotelName,
                       HttpServletRequest req, Model model){

        req.getSession().setAttribute("hotelName", hotelName);
        model.addAttribute("dateDto", new DateDto());

        return "reservation/selectDate";
    }
    @PostMapping("/date")
    String selectDate(@Valid @ModelAttribute("dateDto") DateDto dateDto,
                      HttpServletRequest req, Model model, BindingResult result) {
        if(result.hasErrors()) {
            model.addAttribute("Error","*필수* 날짜를 선택해주세요.");
            String hotelName = (String) req.getSession().getAttribute("hotelName");
            return "redirect:/reservation/hotel/" + hotelName;
        }
        // 방 목록 만들기
        String hotelName = (String) req.getSession().getAttribute("hotelName");
        List<RoomDto> rooms = reservationService.findByRoomList(hotelName);
        model.addAttribute("rooms",rooms);

        // 날짜 정보 세션에 올리기
        req.getSession().setAttribute("dateDto",dateDto);

        return "reservation/selectRoom";
    }
    @GetMapping("/room/{roomType}")
    String selectRoom(@PathVariable("roomType")String roomType, HttpServletRequest req) {
        String hotelName = (String) req.getSession().getAttribute("hotelName");
        DateDto dateDto = (DateDto) req.getSession().getAttribute("dateDto");
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(hotelName,dateDto, RoomType.valueOf(roomType));

        DiscountPriceDto discountPriceDto = reservationService.discountPrice(reservationRequestDto);

        req.getSession().setAttribute("reservationRequestDto",reservationRequestDto);
        req.getSession().setAttribute("discountPriceDto",discountPriceDto);
        return "reservation/reservationPay";
    }
    @GetMapping("/payment")
    String reservePay(HttpServletRequest req, Model model) {
        ReservationRequestDto reservationRequestDto = (ReservationRequestDto) req.getSession().getAttribute("reservationRequestDto");
        DiscountPriceDto discountPriceDto = (DiscountPriceDto) req.getSession().getAttribute("discountPriceDto");
        model.addAttribute("reservationDto",reservationService.reserve(reservationRequestDto, discountPriceDto));
        return "user/reservationInfo";
    }

    @GetMapping("/{reserveNumber}")
    String reserveCancel(@PathVariable("reserveNumber") String reserveNumber) {
        reservationService.reserveDelete(reserveNumber);
        return "/reservation/main";
    }
}
