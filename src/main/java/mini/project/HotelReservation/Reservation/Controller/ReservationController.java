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

    @GetMapping(value = "/hotels")
    public String selectHotel(Model model){

        List<HotelDto> hotelDtoList =
                reservationService.findByHotelList();

        List<String> roomTypes = new ArrayList<>(hotelDtoList.size());
        StringBuilder temp = new StringBuilder();
        for(int i =0;i<hotelDtoList.size() ; i++){
            System.out.println("------------------");
            temp = new StringBuilder();
            for(int j = 0; j<hotelDtoList.get(i).getRoomTypes().size(); j++){
                temp.append(hotelDtoList.get(i).getRoomTypes().get(j).toString());
            }

            roomTypes.add(temp.toString());

            System.out.println("=================");

        }

        model.addAttribute("HotelListDto",hotelDtoList);
        model.addAttribute("roomTypes",roomTypes);


        return "reservation/selectHotel";

    }


//    @GetMapping("/reservation/{")
//    public String selectDate(){
//
//
//
//        return "";
//    }
//

    /*@GetMapping("/hotels")
    String hotelList(Model model){

    }



    String reserveCancel(){

    }*/
}
