package mini.project.HotelReservation.Host.Data.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomStockDto {
    @Pattern(regexp = "^[A-D]$")
    private String roomType;
    private Integer roomStock;
}
