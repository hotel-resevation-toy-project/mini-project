package mini.project.HotelReservation.Host.Data.Dto;

import lombok.Data;

@Data
public class HotelReservationDto {
    private final String reserveNumber;
    private final String userName;
    private final String userPhoneNumber;


    public HotelReservationDto(String reserveNumber, String userName, String phoneNumber) {
        this.reserveNumber = reserveNumber;
        this.userName = userName;
        this.userPhoneNumber = phoneNumber;
    }
}
