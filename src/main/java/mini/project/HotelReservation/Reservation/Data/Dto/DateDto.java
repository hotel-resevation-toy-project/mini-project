package mini.project.HotelReservation.Reservation.Data.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DateDto {
    @NotNull
    private LocalDate checkInDate;
    @NotNull
    private LocalDate checkOutDate;
}
