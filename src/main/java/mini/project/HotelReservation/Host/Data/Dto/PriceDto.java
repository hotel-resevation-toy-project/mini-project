package mini.project.HotelReservation.Host.Data.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    @Pattern(regexp = "^[A-D]$")
    private String roomType;

    private Integer discountPrice;
}
