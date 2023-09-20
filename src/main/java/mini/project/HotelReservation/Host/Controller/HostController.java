package mini.project.HotelReservation.Host.Controller;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Host.Data.Dto.HotelReservationResponseDto;
import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Dto.RoomStockDto;
import mini.project.HotelReservation.Host.Service.HostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/host")
public class HostController {
    private final HostService hostService;
//    private HotelReservationResponseDto hotelReservationResponseDto = new HotelReservationResponseDto();
//    @GetMapping("")
//    String manage(Model model){
//        // TODO: 호스트의 메인 페이지 로직을 구현 (호텔 관련 정보를 모델에 담아서 반환)
//        return "host/manage";
//    }
//    @PatchMapping("/policy")
//    String discountPolicy(String policy){
//        // TODO: 할인 정책 변경 로직 구현
//        return "redirect:/host";
//    }
//    @PatchMapping("/price")
//    String roomPrice(PriceDto priceDto){
//        // TODO: 객실 가격 변경 로직 구현
//        return "redirect:/host";
//    }
//    @PatchMapping("/stock")
//    String roomStock(RoomStockDto roomStockDto){
//        // TODO: 객실 재고 변경 로직 구현
//        return "redirect:/host";
//    }
//    @GetMapping("/reservations")
//    String reserveAll(Model model){
//        // TODO: 호스트의 예약 목록 페이지 로직 구현 (예약 목록을 모델에 담아서 반환)
//        List<HotelReservationResponseDto> reservations = hostService.reservationList(hotelId); // hotelId를 어떻게 가져올지에 대한 고민 필요
//        model.addAttribute("reservations", reservations);
//        return "host/reservations";
//    }
}
