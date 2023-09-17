package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationDto {
    /*- Reservation -
    Long reserveId;
    String reserveNumber;
    Integer reservePrice;
    RoomType roomType;
    String hotelName;
    String phoneNumber;
    String userName;
    LocalDateTime checkInDate;
    LocalDateTime checkOutDate;
    User user;
    Room room;
*/
    /*예약자명
    예약자 전화번호
            호텔명
    객실종류
                    입실날짜
    퇴실날짜
                            예약번호
    결제금액
    */

    private String userName;
    private Integer userPhoneNumber;
    private String hotelName;
    private String roomType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String reserveNumber;
    private Integer totalPrice;


}
