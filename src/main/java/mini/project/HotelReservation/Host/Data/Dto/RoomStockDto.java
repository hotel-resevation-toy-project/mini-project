package mini.project.HotelReservation.Host.Data.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomStockDto {
    @NotBlank(message = "객실 타입을 지정해주세요.")
    private String roomType;
    @NotBlank(message = "변경할 객실 재고를 지정해주세요.")
    private Integer roomStock;
}
