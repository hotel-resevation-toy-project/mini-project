package mini.project.HotelReservation.Host.Data.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import mini.project.HotelReservation.enumerate.RoomType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    @NotBlank(message = "객실 타입을 지정해주세요.")
    private String roomType;
    @NotBlank(message = "변경할 가격을 지정해주세요.")
    private Integer discountPrice;
}
