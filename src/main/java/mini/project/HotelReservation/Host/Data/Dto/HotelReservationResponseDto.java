package mini.project.HotelReservation.Host.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class HotelReservationResponseDto {
    private String reserveNumber;
    private String userName;
    private String userPhoneNumber;


    public HotelReservationResponseDto(String hotelName, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
    }
}
