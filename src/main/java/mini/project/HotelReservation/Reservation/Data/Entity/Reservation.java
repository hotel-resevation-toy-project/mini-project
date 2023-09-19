package mini.project.HotelReservation.Reservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.AuditTime;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "RESERVATIONS")
public class Reservation extends AuditTime {
    @Id @GeneratedValue
    private Long reserveId;

    @NotNull
    private String reserveNumber;

    @NotNull
    private Integer reservePrice;

    @NotNull
    private RoomType roomType;

    @NotNull
    private String hotelName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String userName;

    @NotNull
    private LocalDateTime checkInDate;

    @NotNull
    private LocalDateTime checkOutDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    public Reservation(String reserveNumber, Integer reservePrice, RoomType roomType, String hotelName, String phoneNumber, String userName, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.userName = userName;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.phoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.reserveNumber = reserveNumber;
        this.reservePrice = reservePrice;
    }

    //비즈니스 로직
    public Reservation createReserve(User user, Room room, ReservationResponseDto reservationResponseDto) {

        this.user = user;
        this.room = room;
        this.roomType = reservationResponseDto.getRoomType();
        this.hotelName = reservationResponseDto.getHotelName();
        this.reservePrice = reservationResponseDto.getReservePrice();
        this.checkInDate = reservationResponseDto.getCheckInDate();
        this.checkOutDate = reservationResponseDto.getCheckOutDate();

        return new Reservation(getReserveNumber(),
                getReservePrice(),
                getRoomType(),
                getHotelName(),
                getPhoneNumber(),
                getUserName(),
                getCheckInDate(),
                getCheckOutDate());
    }
    // 연관관계 메서드
    public void foreignUser(User foreignUser){
        user = foreignUser;
    }
    public void foreignRoom(Room foreignRoom){
        room = foreignRoom;
    }
    

}
