package mini.project.HotelReservation.Reservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.AuditTime;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Reservation(String reserveNumber, Integer reservePrice, RoomType roomType, String hotelName, String phoneNumber, String userName, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.reserveNumber = reserveNumber;
        this.reservePrice = reservePrice;
        this.roomType = roomType;
        this.hotelName = hotelName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //비즈니스 로직
    public static Reservation createReserve(User user, Hotel hotel, ReservationRequestDto reservationRequestDto, Integer reservePrice, String reserveNumber) {

        Reservation reservation = new Reservation(reserveNumber,
                reservePrice,
                reservationRequestDto.getRoomType(),
                hotel.getHotelName(),
                user.getPhoneNumber(),
                user.getName(),
                LocalDateTime.of(reservationRequestDto.getCheckInDate(),hotel.getCheckInTime()),
                LocalDateTime.of(reservationRequestDto.getCheckOutDate(),hotel.getCheckOutTime())
                );

        reservation.foreignHotel(hotel);
        reservation.foreignUser(user);
        return reservation;
    }

    // 연관관계 메서드
    public void foreignUser(User foreignUser){
        this.user = foreignUser;
        user.getReservations().add(this);
    }
    public void foreignHotel(Hotel foreignHotel){
        this.hotel = foreignHotel;
        hotel.getReservations().add(this);
    }
    // 예약 삭제시 연관관계 제거 메서드
    public void deleteReservation() {
        this.getHotel().getReservations().remove(this);
        this.getUser().getReservations().remove(this);
    }
}
