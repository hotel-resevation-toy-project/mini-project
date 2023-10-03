package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class HotelDto {
    private final String hotelName;
    private final List<String> roomTypes;
    private final LocalTime checkInTime;
    private final LocalTime checkOutTime;
}
