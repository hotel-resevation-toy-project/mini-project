package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class DiscountPriceDto {
    private Integer totalPrice;
    private Integer discountPrice;
    private Integer pay;
    private String discountPolicy;
}