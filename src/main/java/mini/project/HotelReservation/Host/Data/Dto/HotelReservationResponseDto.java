package mini.project.HotelReservation.Host.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelReservationResponseDto {
    private String reserveNumber;
    private String userName;
    private String userPhoneNumber;
}
