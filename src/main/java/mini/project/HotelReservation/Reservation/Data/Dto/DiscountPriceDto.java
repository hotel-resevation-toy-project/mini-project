package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class DiscountPriceDto {
    private Integer totalPrice;
    private Integer Discount;
    private Integer Pay;
    private String DiscountPolicy;
}