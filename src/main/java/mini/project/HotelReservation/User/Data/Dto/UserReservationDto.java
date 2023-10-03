package mini.project.HotelReservation.User.Data.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserReservationDto {
    private final String reserveNumber;
    private final String hotelName;
    private final LocalDateTime checkInDate;
    private final LocalDateTime checkOutDate;

    public UserReservationDto(String reserveNumber, String hotelName, LocalDateTime checkInDate, LocalDateTime checkOutDate){
        this.reserveNumber = reserveNumber;
        this.hotelName = hotelName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
