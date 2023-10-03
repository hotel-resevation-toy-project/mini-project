package mini.project.HotelReservation.Host.Data.Dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    @Pattern(regexp = "^[A-D]$")
    private String roomType;
    private Integer discountPrice;
}
